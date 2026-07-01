<?php
defined('BASEPATH') or exit('No direct script access allowed');

class appnotification extends CI_Controller
{

    public function  __construct()
    {
        parent::__construct();



        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('notification_model', 'notif');
        $this->load->model('Group_model', 'group');
        $this->load->library('form_validation');
        $this->load->library('firebase');
    }

    public function index()
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();

        $this->load->view('includes/header', $data);
        $this->load->view('appnotification/index');
        $this->load->view('includes/footer');
    }

    public function send()
    {
        // Ambil data dari POST
        $topic = $this->input->post('topic');
        $title = $this->input->post('title');
        $message = $this->input->post('message');
        
        // Load library firebase
        $this->load->library('firebase');
        
        // Data tambahan jika diperlukan
        $data = array(
            'click_action' => 'ANDROID_NOTIFICATION_CLICK',
            'screen' => 'notification',
            'created_at' => date('Y-m-d H:i:s')
        );
    
        try {
            // Kirim notifikasi berdasarkan topic
            $result = $this->firebase->send_topic_notification(
                $topic,
                $title,
                $message,
                $data
            );
    
            if (isset($result['name'])) {
                // Sukses
                $this->session->set_flashdata('send', 'Notifikasi berhasil dikirim!');
            } else {
                // Gagal
                $this->session->set_flashdata('error', 'Gagal mengirim notifikasi: ' . json_encode($result));
            }
        } catch (Exception $e) {
            $this->session->set_flashdata('error', 'Error: ' . $e->getMessage());
        }
    
        redirect('appnotification/index');
    }
}
