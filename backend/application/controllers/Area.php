<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Area extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
     

        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('Area_model', 'cm');
         $this->load->model('Group_model', 'group');
        $this->load->library('form_validation');
    }

    public function index()
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['group'] = $this->group->get_all_group();
        $data['catmer'] = $this->cm->getallcm();
        $data['fitur'] = $this->cm->getallvoucher();



        $this->load->view('includes/header', $data);
        $this->load->view('area/index', $data);
        $this->load->view('includes/footer');
    }

    public function tambahcm()
    {
        

        $this->form_validation->set_rules('id', 'id','trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {

            $data = [
                'kota'     => html_escape($this->input->post('kota', TRUE)),
                'promo'     => html_escape($this->input->post('promo', TRUE)),
                'rate1'     => html_escape($this->input->post('rate1', TRUE)),
                'rate2'     => html_escape($this->input->post('rate2', TRUE)),
                'rate3'     => html_escape($this->input->post('rate3', TRUE)),
                'status'   => html_escape($this->input->post('status', TRUE)),
            ];
            $this->cm->tambahcm($data);
            $this->session->set_flashdata('tambah', 'Area Has Been Added');
            redirect('area');
        }
    }

    public function hapus($id)
    {
        $this->cm->hapuscm($id);
        $this->session->set_flashdata('hapus', 'Area Has Been Deleted');
        redirect('area');
    }


    public function ubahcm()
    {


        $this->form_validation->set_rules('kota', 'kota', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {

            $id = $this->input->post('id');
            $data = [
                'kota'     => html_escape($this->input->post('kota', TRUE)),
                'promo'     => html_escape($this->input->post('promo', TRUE)),
                'rate1'     => html_escape($this->input->post('rate1', TRUE)),
                'rate2'     => html_escape($this->input->post('rate2', TRUE)),
                'rate3'     => html_escape($this->input->post('rate3', TRUE)),
                'id'          => html_escape($this->input->post('id', TRUE)),
                'status'   => html_escape($this->input->post('status', TRUE)),
            ];

            $this->cm->ubahcm($data, $id);
            $this->session->set_flashdata('ubah', 'Area Has Been Updated');
            redirect('area');
        }
    }
}
