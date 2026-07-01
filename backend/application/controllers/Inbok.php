<?php
defined('BASEPATH') or exit('No direct script access allowed');
class Inbok extends CI_Controller 
{
  public function  __construct()
    {
        parent::__construct();
       
        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('inbok_model', 'user');
        $this->load->model('Group_model', 'group');
        $this->load->library('form_validation');
    }
    /*Display*/
 public function index()
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');
        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['user'] = $this->user->getdata();
        $this->load->view('includes/header', $data);
        $this->load->view('inbok/index', $data);
        $this->load->view('includes/footer');
    }
    public function kirim()
    {
        $this->form_validation->set_rules('id', 'id', 'trim|prep_for_form');
        $this->form_validation->set_rules('title', 'title', 'trim|prep_for_form');
        $this->form_validation->set_rules('konten', 'konten', 'trim|prep_for_form');
        $this->form_validation->set_rules('date', 'date', 'trim|prep_for_form');
        $num = mt_rand(100000,999999); 
        $now = date_create()->format('dd-mm-YYYY');
        if ($this->form_validation->run() == TRUE) {
            $data             = [
                'id'              => html_escape($this->input->post('id', TRUE)),
                'idpesan'              => $num,
                'title'              => html_escape($this->input->post('title', TRUE)),
                'konten'                       => html_escape($this->input->post('konten', TRUE)),
                'date'              => date_create('now')->format('Y/m/d'),
            ];
            $this->user->kirimdata($data);
            $this->session->set_flashdata('tambah', 'Pesan Berhasil Dikirim.');
            redirect('inbok');
        } else {
            $this->load->view('includes/header');
            $this->load->view('inbok/index', $data);
            $this->load->view('includes/footer');
        }
    }
}
