<?php
//'tes' => number_format(200 / 100, 0, ",", "."),
defined('BASEPATH') or exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . '/libraries/Veritrans.php';
class Otp extends REST_Controller
{
    public function __construct()
    {
        parent::__construct();
        $this->load->helper("url");
        $this->load->database();
        $this->load->model('Pelanggan_model');
        $this->load->model('Notification_model');
        $this->load->model('Driver_model');
        $this->load->model('Merchantapi_model');
        $this->load->model('Digital_model');
        $this->load->model('Donasi_model');
        $this->load->model('Func_model');
        $this->load->model('Appsettings_model');
        date_default_timezone_set('Asia/Jakarta');
    }
    
    function index_get()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        
        $iduser = $this->input->get_request_header('id', TRUE);
        $nomor = $this->input->get_request_header('nomor', TRUE);
        
        if(substr($iduser, 0, 1) == 'P'){
            $conds = array(
                'id' => $iduser,
                'status' => 1
            );
            $cek_login = $this->Pelanggan_model->get_data_pelanggan($conds);
        }else if(substr($iduser, 0, 1) == 'D'){
            $conds = array(
                'id' => $iduser,
            );
            $cek_login = $this->get_data_pelanggan($conds);
        }else{
            // echo "sini";die();
            $conds = array(
                'mitra.id_mitra' => $iduser,
            );

            $cek_login = $this->Merchantapi_model->get_data_merchant($conds);
        
        }
       
       
        if ($cek_login->num_rows() > 0) {
            
            $condotp = array(
                'id_user' => $iduser,
                'status' => 0
            );
            $check_otp = $this->Func_model->get_data_otp($condotp);
            if($check_otp->num_rows() > 0){
                $res = $check_otp->result_array();
                $idOtp = $res[0]['id'];
                $data_update = array(
                    'status' => 9
                );
                $updateOtp = $this->Func_model->update_otp($idOtp, $data_update);
            }
            
            $resOtp = $this->Func_model->insert_otp($iduser);
            if($resOtp['status'] == '00'){
                
                $appconf = $this->Appsettings_model->getappbyid();
                
                $data_send = array(
                    'key' => $appconf['key_api_wasap'],
                    'sender' => $appconf['sender_wasap'],
                    'number' => $nomor,
                    'message' => 'Jangan bagikan kode ini kepada siapapun termasuk pihak yang mengaku dari *OGOT*. KODE OTP: '.$resOtp['otp']
                );
                
        	    $this->Notification_model->send_wa($data_send);
                $message = array(
                    'code' => '200',
                    'status' => '00',
                    'message' => 'Kode OTP Terkirim',
                );
                $this->response($message, 200);
            }else{
                $message = array(
                    'code' => '10',
                    'status' => '10',
                    'message' => 'Request otp Gagal, Silahkan Ulangi',
                );
                $this->response($message, 200); 
            }
            
        }else{
            $message = array(
                'code' => '404',
                'status' => '20',
                'message' => 'User not found or blocked',
            );
            $this->response($message, 201);
        }
        
    }
    
    function index_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $iduser = $this->input->get_request_header('id', TRUE);
        $nomor = $this->input->get_request_header('nomor', TRUE);
        
        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        
        $condotp = array(
            'id_user' => $iduser,
            'kode' => $dec_data->otp,
            'status' => 0
        );
        $check_otp = $this->Func_model->get_data_otp($condotp);
        if($check_otp->num_rows() > 0){
            $res = $check_otp->result_array();
            $idOtp = $res[0]['id'];
            $data_update = array(
                'status' => 9
            );
            $updateOtp = $this->Func_model->update_otp($idOtp, $data_update);
            $conds = array(
                'id' => $iduser
            );
            if(substr($iduser, 0, 1) == 'P'){
                $conds = array(
                    'id' => $iduser,
                    'status' => 1
                );
                $cek_login = $this->Pelanggan_model->get_data_pelanggan($conds);
            }else if(substr($iduser, 0, 1) == 'D'){
                $conds = array(
                    'id' => $iduser,
                   
                );
                $cek_login = $this->get_data_pelanggan($conds);
            }else{
                $conds = array(
                    'mitra.id_mitra' => $iduser,
                    'mitra.status_mitra' => 1
                );
    
                $cek_login = $this->Merchantapi_model->get_data_merchant($conds);
            
            }
    
        	$message = array(
                'code' => '200',
                'status' => '00',
                'message' => 'Berhasil',
                'data' => $cek_login->result()

            );
            $this->response($message, 200);
        }else{
            $message = array(
                'code' => '201',
                'status' => '11',
                'message' => 'Kode otp tidak valid'

            );

            $this->response($message, 201);
        }
    }
    
    
    function get_data_pelanggan($condition)
    {
        $this->db->from('driver');
        $this->db->where($condition);
        return $this->db->get();
    }
    
}