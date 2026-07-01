<?php
class Forgot extends REST_Controller
{
    public function __construct()
    {
        parent::__construct();
        $this->load->helper("url");
        $this->load->model('Pelanggan_model');
        $this->load->model('Notification_model');
        $this->load->model('Appsettings_model');  // Pastikan model ini dimuat
        date_default_timezone_set('Asia/Jakarta');
    }

    public function forgot_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return $this->response(null, REST_Controller::HTTP_UNAUTHORIZED);
        }

        $data = json_decode(file_get_contents("php://input"), true);
        if (!isset($data['email'])) {
            return $this->response([
                'code' => '400',
                'message' => 'Email is required'
            ], REST_Controller::HTTP_BAD_REQUEST);
        }

        $condition = [
            'email' => $data['email'],
            'status' => '1'
        ];

        $cek_login = $this->Pelanggan_model->get_data_pelanggan($condition);
        if ($cek_login->num_rows() > 0) {
            $user = $cek_login->row();
            $token = sha1(rand(0, 999999) . time());
            $dataforgot = [
                'userid' => $user->id,
                'token' => $token,
                'idKey' => '1'
            ];

            $forgot = $this->Pelanggan_model->dataforgot($dataforgot);
            $linkbtn = base_url() . 'resetpass/rest/' . $token . '/1';
            $message = "Silakan klik link berikut untuk mereset password Anda: " . $linkbtn;

            // Mendapatkan pengaturan WhatsApp dari database
            $whatsapp_settings = $this->Appsettings_model->get_whatsapp_settings();

            $wa_data = [
                'key' => $whatsapp_settings->key_api_wasap, 
                'sender' => $whatsapp_settings->sender_wasap, 
                'number' => $user->phone, 
                'message' => $message
            ];

            $sendmail = $this->Notification_model->send_wa($wa_data);
            if ($forgot && $sendmail) {
                $response_data = [
                    'code' => '200',
                    'message' => 'Link reset password telah dikirim ke WhatsApp Anda'
                ];
                $this->response($response_data, REST_Controller::HTTP_OK);
            } else {
                $this->response([
                    'code' => '500',
                    'message' => 'Gagal mengirim link reset password'
                ], REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
            }
        } else {
            $this->response([
                'code' => '404',
                'message' => 'Email tidak terdaftar'
            ], REST_Controller::HTTP_NOT_FOUND);
        }
    }
}
?>
