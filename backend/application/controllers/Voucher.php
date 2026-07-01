<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Voucher extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
     

        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('Voucher_model', 'cm');
        $this->load->model('Group_model', 'group');
        $this->load->library('form_validation');
    }

    public function index()
    {
         $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['catmer'] = $this->cm->getallcm();
        $data['fitur'] = $this->cm->getallvoucher();



        $this->load->view('includes/header', $data);
        $this->load->view('voucher/index', $data);
        $this->load->view('includes/footer');
    }

    public function tambahcm()
    {


        $this->form_validation->set_rules('id', 'id','trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {

            $data = [
                'nama'     => html_escape($this->input->post('nama', TRUE)),
                'point'     => html_escape($this->input->post('point', TRUE)),
                'reward'     => html_escape($this->input->post('reward', TRUE)),
                'tipe'     => html_escape($this->input->post('tipe', TRUE)),
                'status'   => html_escape($this->input->post('status', TRUE)),
            ];
            $this->cm->tambahcm($data);
            $this->session->set_flashdata('tambah', 'Voucher Has Been Added');
            redirect('voucher');
        }
    }

    public function hapus($id)
    {
        $this->cm->hapuscm($id);
        $this->session->set_flashdata('hapus', 'Voucher Has Been Deleted');
        redirect('voucher');
    }


   public function ubahcm()
    {


        $this->form_validation->set_rules('nama', 'nama', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {

            $id = $this->input->post('id');
            $data = [
                'nama'     => html_escape($this->input->post('nama', TRUE)),
                'point'     => html_escape($this->input->post('point', TRUE)),
                'tipe'     => html_escape($this->input->post('tipe', TRUE)),
                'reward'     => html_escape($this->input->post('reward', TRUE)),
                'id'          => html_escape($this->input->post('id', TRUE)),
                'status'   => html_escape($this->input->post('status', TRUE)),
            ];

            $this->cm->ubahcm($data, $id);
            $this->session->set_flashdata('ubah', 'Voucher Has Been Updated');
            redirect('voucher');
        }
    }
}
