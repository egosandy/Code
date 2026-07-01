<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Categorymerchant extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
     

        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('categorymerchant_model', 'cm');
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
        $data['fitur'] = $this->cm->getfiturmerchant();



        $this->load->view('includes/header', $data);
        $this->load->view('categorymerchant/index', $data);
        $this->load->view('includes/footer');
    }

    public function tambahcm()
    {


        $this->form_validation->set_rules('nama_kategori', 'nama_kategori', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/kategorimerchant/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);

            if ($this->upload->do_upload('foto_kategori')) {
                $gambar = html_escape($this->upload->data('file_name'));
            } else {
                $gambar = 'noimage.jpg';
            }
            $data = [
                'foto_kategori'                       => $gambar,
                'nama_kategori'     => html_escape($this->input->post('nama_kategori', TRUE)),
                'id_fitur'          => html_escape($this->input->post('id_fitur', TRUE)),
                'status_kategori'   => html_escape($this->input->post('status_kategori', TRUE)),
            ];
            $this->cm->tambahcm($data);
            $this->session->set_flashdata('tambah', 'Category Merchant Has Been Added');
            redirect('categorymerchant');
        }
    }

    public function hapus($id)
    {
        $this->cm->hapuscm($id);
        $this->session->set_flashdata('hapus', 'Category Merchant Has Been Deleted');
        redirect('categorymerchant');
    }


    public function ubahcm()
    {


        $this->form_validation->set_rules('nama_kategori', 'nama_kategori', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/kategorimerchant/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = time();
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);
            $id = $this->input->post('id_kategori_merchant');
             if ($this->upload->do_upload('foto_kategori')) {
                unlink('images/kategorimerchant/' . $this->cm->getdatabyid($id)->row('foto_kategori'));
                $gambar = html_escape($this->upload->data('file_name'));
                $data = [
                'foto_kategori'                       => $gambar,
                'nama_kategori'     => html_escape($this->input->post('nama_kategori', TRUE)),
                'id_fitur'          => html_escape($this->input->post('id_fitur', TRUE)),
                'status_kategori'   => html_escape($this->input->post('status_kategori', TRUE)),
                ];
            } else {
                 $data = [
                'foto_kategori'                       => $gambar,
                'nama_kategori'     => html_escape($this->input->post('nama_kategori', TRUE)),
                'id_fitur'          => html_escape($this->input->post('id_fitur', TRUE)),
                'status_kategori'   => html_escape($this->input->post('status_kategori', TRUE)),
                ];
            }
           

            $this->cm->ubahcm($data, $id);
            $this->session->set_flashdata('ubah', 'Category Merchant Has Been Updated');
            redirect('categorymerchant');
        }
    }
}
