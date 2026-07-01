<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Appsettings extends CI_Controller
{

    public function  __construct()
    {
        parent::__construct();

       
        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->library('form_validation');
        $this->load->model('appsettings_model', 'app');
        $this->load->model('digital_model', 'digi');
        $this->load->model('Group_model', 'group');
    }

    public function index()
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['appsettings'] = $this->app->getappbyid();
        $data['transfer'] = $this->app->gettransfer();
        $data['digi'] = $this->digi->get_setting_digi();

        $this->load->view('includes/header', $data);
        $this->load->view('appsettings/index', $data);
        $this->load->view('includes/footer');
    }

    public function ubahbank($id)
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        
        $this->form_validation->set_rules('nama_bank', 'nama_bank', 'trim|prep_for_form');
        $this->form_validation->set_rules('rekening_bank', 'rekening_bank', 'trim|prep_for_form');
        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/bank/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = time();
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);
            $dataget = $this->app->getbankid($id);

            if ($this->upload->do_upload('image_bank')) {
                if ($dataget['image_bank'] != 'noimage.jpg') {
                    $gambar = $dataget['image_bank'];
                    unlink('./images/bank/' . $gambar);
                }
                $gambar = $dataget['image_bank'];
                unlink('./images/bank/' . $gambar);
                $app_logo = html_escape($this->upload->data('file_name'));
            } else {
                $app_logo = $dataget['image_bank'];
            }

            $data = [
                'nama_bank' => html_escape($this->input->post('nama_bank', TRUE)),
                'rekening_bank' => html_escape($this->input->post('rekening_bank', TRUE)),
                'status_bank' => html_escape($this->input->post('status_bank', TRUE)),
                'image_bank' => $app_logo
            ];

            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('appsettings/index');
            } else {

                $this->app->ubahdatarekening($data, $id);
                $this->session->set_flashdata('ubah', 'APP Has Been Change');
                redirect('appsettings');
            }
        }
    }

    public function hapusbank($id)
    {
        if (demo == TRUE) {
            $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
            redirect('appsettings/index');
        } else {
            $dataget = $this->app->getbankid($id);
            $gambar = $dataget['image_bank'];
            unlink('./images/bank/' . $gambar);
            $this->app->hapusrekening($id);
            $this->session->set_flashdata('ubah', 'APP Has Been deleted');
            redirect('appsettings');
        }
    }

    public function adddatabank()
    {
        
        $this->form_validation->set_rules('nama_bank', 'nama_bank', 'trim|prep_for_form');
        $this->form_validation->set_rules('rekening_bank', 'rekening_bank', 'trim|prep_for_form');
        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/bank/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = time();
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);

            if ($this->upload->do_upload('image_bank')) {
                $app_logo = html_escape($this->upload->data('file_name'));
            }

            $data = [
                'nama_bank' => html_escape($this->input->post('nama_bank', TRUE)),
                'rekening_bank' => html_escape($this->input->post('rekening_bank', TRUE)),
                'status_bank' => html_escape($this->input->post('status_bank', TRUE)),
                'image_bank' => $app_logo
            ];

            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('appsettings/index');
            } else {

                $this->app->adddatarekening($data);
                $this->session->set_flashdata('ubah', 'APP Has Been add');
                redirect('appsettings');
            }
        }
    }

    public function ubahapp()
    {
         $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['group'] = $this->group->get_all_group();

        $this->form_validation->set_rules('app_email', 'app_email', 'trim|prep_for_form');
        $this->form_validation->set_rules('app_website', 'app_website', 'trim|prep_for_form');
        $this->form_validation->set_rules('app_linkgoogle', 'app_linkgoogle', 'trim|prep_for_form');
        $this->form_validation->set_rules('app_currency', 'app_currency', 'trim|prep_for_form');
        $this->form_validation->set_rules('fee_rain', 'fee_rain', 'trim|prep_for_form');
        $this->form_validation->set_rules('fee_rain_status', 'fee_rain_status', 'trim|prep_for_form');
        $this->form_validation->set_rules('fee_add_time', 'fee_add_time', 'trim|prep_for_form');
        $this->form_validation->set_rules('fee_time_on', 'fee_time_on', 'trim|prep_for_form');
        $this->form_validation->set_rules('fee_time_off', 'fee_time_off', 'trim|prep_for_form');
        $this->form_validation->set_rules('fee_time_status', 'fee_time_status', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './asset/images/icon/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);
            $data = $this->app->getappbyid();


            if ($this->upload->do_upload('app_logo')) {
                if ($data['app_logo'] != 'noimage.jpg') {
                    $gambar = $data['app_logo'];
                    unlink('asset/images/icon/' . $gambar);
                }

                $app_logo = html_escape($this->upload->data('file_name'));
            } else {
                $app_logo = $data['app_logo'];
            }

            $data             = [
                'app_logo'                    => $app_logo,
                'app_email'                    => html_escape($this->input->post('app_email', TRUE)),
                'app_website'                => html_escape($this->input->post('app_website', TRUE)),
                'app_privacy_policy'        => $this->input->post('app_privacy_policy', TRUE),
                'app_aboutus'                => $this->input->post('app_aboutus', TRUE),
                'app_address'                => $this->input->post('app_address'),
                'app_linkgoogle'            => html_escape($this->input->post('app_linkgoogle', TRUE)),
                'app_name'                  => html_escape($this->input->post('app_name', TRUE)),
                'map_key'                  => html_escape($this->input->post('map_key', TRUE)),
                'app_contact'                  => html_escape($this->input->post('app_contact', TRUE)),
                'maintenance'                  => html_escape($this->input->post('maintenance', TRUE)),
                'isotp'                  => html_escape($this->input->post('isotp', TRUE)),
                'sender_wasap'                  => html_escape($this->input->post('sender_wasap', TRUE)),
                'key_api_wasap'                  => html_escape($this->input->post('key_api_wasap', TRUE)),
                'app_currency'                => html_escape($this->input->post('app_currency', TRUE)),
                'versi_cs'                => html_escape($this->input->post('versi_cs', TRUE)),
                'versi_driver'                => html_escape($this->input->post('versi_driver', TRUE)),
                'versi_mitra'                => html_escape($this->input->post('versi_mitra', TRUE)),
                'force_update_user'                => html_escape($this->input->post('force_user', TRUE)),
                'force_update_driver'                => html_escape($this->input->post('force_driver', TRUE)),
                'force_update_mitra'                => html_escape($this->input->post('force_mitra', TRUE)),
                'fee_rain'                => html_escape($this->input->post('fee_rain', TRUE)),
                'fee_rain_status'                => html_escape($this->input->post('fee_rain_status', TRUE)),
                'fee_add_time'                => html_escape($this->input->post('fee_add_time', TRUE)),
                'fee_time_on'                => html_escape($this->input->post('fee_time_on', TRUE)),
                'fee_time_off'                => html_escape($this->input->post('fee_time_off', TRUE)),
                'fee_time_status'                => html_escape($this->input->post('fee_time_status', TRUE)),
            ];

            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('appsettings/index');
            } else {

                $this->app->ubahdataappsettings($data);
                $this->session->set_flashdata('ubah', 'APP Has Been Change');
                redirect('appsettings');
            }
        } else {

            $data['appsettings'] = $this->app->getappbyid();

            $this->load->view('includes/header', $data);
            $this->load->view('appsettings/index', $data);
            $this->load->view('includes/footer');
        }
    }

    public function ubahemail()
    {
         $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['group'] = $this->group->get_all_group();

        $this->form_validation->set_rules('email_subject', 'email_subject', 'trim|prep_for_form');
        $this->form_validation->set_rules('email_subject_confirm', 'email_subject', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $data             = [
                'email_subject'                    => html_escape($this->input->post('email_subject', TRUE)),
                'email_subject_confirm'                    => html_escape($this->input->post('email_subject_confirm', TRUE)),
                'email_text1'                    => $this->input->post('email_text1'),
                'email_text2'                    => $this->input->post('email_text2'),
                'email_text3'                    => $this->input->post('email_text3'),
                'email_text4'                    => $this->input->post('email_text4')
            ];


            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('appsettings/index');
            } else {

                $this->app->ubahdataemail($data);
                $this->session->set_flashdata('ubah', 'Email Has Been Change');
                redirect('appsettings');
            }
        } else {

            $data['appsettings'] = $this->app->getappbyid();

            $this->load->view('includes/header', $data);
            $this->load->view('appsettings/index', $data);
            $this->load->view('includes/footer');
        }
    }

    public function ubahsmtp()
    {
        
         $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['group'] = $this->group->get_all_group();

        $this->form_validation->set_rules('smtp_host', 'smtp_host', 'trim|prep_for_form');
        $this->form_validation->set_rules('smtp_port', 'smtp_port', 'trim|prep_for_form');
        $this->form_validation->set_rules('smtp_username', 'smtp_username', 'trim|prep_for_form');
        $this->form_validation->set_rules('smtp_password', 'smtp_password', 'trim|prep_for_form');
        $this->form_validation->set_rules('smtp_form', 'smtp_form', 'trim|prep_for_form');
        $this->form_validation->set_rules('smtp_secure', 'smtp_secure', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $data             = [
                'smtp_host'                        => html_escape($this->input->post('smtp_host', TRUE)),
                'smtp_port'                        => html_escape($this->input->post('smtp_port', TRUE)),
                'smtp_username'                    => html_escape($this->input->post('smtp_username', TRUE)),
                'smtp_password'                    => html_escape($this->input->post('smtp_password', TRUE)),
                'smtp_from'                        => html_escape($this->input->post('smtp_from', TRUE)),
                'smtp_secure'                    => html_escape($this->input->post('smtp_secure', TRUE))
            ];


            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('appsettings/index');
            } else {
                $this->app->ubahdatasmtp($data);
                $this->session->set_flashdata('ubah', 'SMTP Has Been Change');
                redirect('appsettings');
            }
        } else {

            $data['appsettings'] = $this->app->getappbyid();

            $this->load->view('includes/header', $data);
            $this->load->view('appsettings/index', $data);
            $this->load->view('includes/footer');
        }
    }

    public function ubahstripe()
    {
        
         $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['group'] = $this->group->get_all_group();

        $this->form_validation->set_rules('stripe_secret_key', 'stripe_secret_key', 'trim|prep_for_form');
        $this->form_validation->set_rules('stripe_published_key', 'stripe_published_key', 'trim|prep_for_form');
        $this->form_validation->set_rules('stripe_status', 'stripe_status', 'trim|prep_for_form');
        $this->form_validation->set_rules('stripe_active', 'stripe_active', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $data             = [
                'stripe_secret_key'                    => html_escape($this->input->post('stripe_secret_key', TRUE)),
                'stripe_published_key'                => html_escape($this->input->post('stripe_published_key', TRUE)),
                'stripe_status'                        => html_escape($this->input->post('stripe_status', TRUE)),
                'stripe_active'                        => html_escape($this->input->post('stripe_active', TRUE))
            ];
            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('appsettings/index');
            } else {


                $this->app->ubahdatastripe($data);
                $this->session->set_flashdata('ubah', 'Stripe Has Been Change');
                redirect('appsettings');
            }
        } else {

            $data['appsettings'] = $this->app->getappbyid();

            $this->load->view('includes/header', $data);
            $this->load->view('appsettings/index', $data);
            $this->load->view('includes/footer');
        }
    }

    public function ubahpaypal()
    {
        
         $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['group'] = $this->group->get_all_group();

        $this->form_validation->set_rules('paypal_key', 'paypal_key', 'trim|prep_for_form');
        $this->form_validation->set_rules('app_currency_text', 'app_currency_text', 'trim|prep_for_form');
        $this->form_validation->set_rules('paypal_mode', 'paypal_mode', 'trim|prep_for_form');
        $this->form_validation->set_rules('paypal_active', 'paypal_active', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $data             = [
                'paypal_key'                    => html_escape($this->input->post('paypal_key', TRUE)),
                'app_currency_text'                => html_escape($this->input->post('app_currency_text', TRUE)),
                'paypal_mode'                        => html_escape($this->input->post('paypal_mode', TRUE)),
                'paypal_active'                        => html_escape($this->input->post('paypal_active', TRUE))
            ];
            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('appsettings/index');
            } else {


                $this->app->ubahdatapaypal($data);
                $this->session->set_flashdata('ubah', 'Paypal Has Been Change');
                redirect('appsettings');
            }
        } else {

            $data['appsettings'] = $this->app->getappbyid();

            $this->load->view('includes/header' ,$data);
            $this->load->view('appsettings/index', $data);
            $this->load->view('includes/footer');
        }
    }
    public function ubahmidtrans()
    {
        
         $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['group'] = $this->group->get_all_group();

        $this->form_validation->set_rules('midtrans_url', 'midtrans_url', 'trim|prep_for_form');
        $this->form_validation->set_rules('midtrans_key', 'midtrans_key', 'trim|prep_for_form');
        $this->form_validation->set_rules('midtrans_aktif', 'midtrans_aktif', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $data             = [
                'midtrans_url'                    => html_escape($this->input->post('midtrans_url', TRUE)),
                'midtrans_key'                => html_escape($this->input->post('midtrans_key', TRUE)),
                'midtrans_aktif'                        => html_escape($this->input->post('midtrans_aktif', TRUE))
            ];
            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('appsettings/index');
            } else {


                $this->app->ubahmidtrans($data);
                $this->session->set_flashdata('ubah', 'Midtrans Has Been Change');
                redirect('appsettings');
            }
        } else {

            $data['appsettings'] = $this->app->getappbyid();

            $this->load->view('includes/header', $data);
            $this->load->view('appsettings/index', $data);
            $this->load->view('includes/footer');
        }
    }
    public function ubahmobilepulsa()
    {
        
         $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['group'] = $this->group->get_all_group();

        $this->form_validation->set_rules('mobilepulsa_url', 'mobilepulsa_url', 'trim|prep_for_form');
        $this->form_validation->set_rules('mobilepulsa_user', 'mobilepulsa_user', 'trim|prep_for_form');
        $this->form_validation->set_rules('mobilepulsa_pass', 'mobilepulsa_pass', 'trim|prep_for_form');
        $this->form_validation->set_rules('mobilepulsa_aktif', 'mobilepulsa_aktif', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $data             = [
                'mobilepulsa_url'                    => html_escape($this->input->post('mobilepulsa_url', TRUE)),
                'mobilepulsa_user'                => html_escape($this->input->post('mobilepulsa_user', TRUE)),
                'mobilepulsa_pass'                => html_escape($this->input->post('mobilepulsa_pass', TRUE)),
                'mobilepulsa_aktif'                        => html_escape($this->input->post('mobilepulsa_aktif', TRUE))
            ];
            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('appsettings/index');
            } else {


                $this->app->ubahmobilepulsa($data);
                $this->session->set_flashdata('ubah', 'Mobilepulsa Has Been Change');
                redirect('appsettings');
            }
        } else {

            $data['appsettings'] = $this->app->getappbyid();

            $this->load->view('includes/header', $data);
            $this->load->view('appsettings/index', $data);
            $this->load->view('includes/footer');
        }
    }
    public function ubahbanner()
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['group'] = $this->group->get_all_group();
        

        $this->form_validation->set_rules('bannerid', 'bannerid', 'trim|prep_for_form');
        $this->form_validation->set_rules('bannerunit', 'bannerunit', 'trim|prep_for_form');
        $this->form_validation->set_rules('banneraktif', 'banneraktif', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $data             = [
                'bannerid'                    => html_escape($this->input->post('bannerid', TRUE)),
                'bannerunit'                => html_escape($this->input->post('bannerunit', TRUE)),
                'banneraktif'                        => html_escape($this->input->post('banneraktif', TRUE))
            ];
            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('appsettings/index');
            } else {


                $this->app->ubahbanner($data);
                $this->session->set_flashdata('ubah', 'Banner Has Been Change');
                redirect('appsettings');
            }
        } else {

            $data['appsettings'] = $this->app->getappbyid();

            $this->load->view('includes/header', $data);
            $this->load->view('appsettings/index', $data);
            $this->load->view('includes/footer');
        }
    }
    public function addbank()

    {
         $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['group'] = $this->group->get_all_group();
        $this->load->view('includes/header', $data);
        $this->load->view('appsettings/addbank');
        $this->load->view('includes/footer');
    }

    public function editbank($id)

    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['group'] = $this->group->get_all_group();
        $data['transfer'] = $this->app->getbankid($id);
        $this->load->view('includes/header', $data);
        $this->load->view('appsettings/editbank', $data);
        $this->load->view('includes/footer');
    }
    
    function ubahdigiflazz(){
        
        $username = html_escape($this->input->post('username', TRUE));
        $keydevelopment = html_escape($this->input->post('key_development', TRUE));
        $keyproduction = html_escape($this->input->post('key_production', TRUE));
        $isdemo = html_escape($this->input->post('is_demo', TRUE));
        
        $data = [
            'username' => $username,
            'key_development' => $keydevelopment,
            'key_production' => $keyproduction,
            'is_demo' => $isdemo
        ];
        
        $this->digi->update_data_setting($data);
        $this->session->set_flashdata('ubah', 'Data Has Been Change');
        redirect('appsettings');
        
    }
}
