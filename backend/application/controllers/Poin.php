<?php
error_reporting(0);
defined('BASEPATH') or exit('No direct script access allowed');

class Poin extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
        

        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('Poin_model', 'poin');
        $this->load->model('Group_model', 'group');
        $this->load->library('form_validation');
    }

    public function index()
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['poin'] = $this->poin->getAllPoin();

        $this->load->view('includes/header', $data);
        $this->load->view('poin/index',$data);
        $this->load->view('includes/footer');
    }
    public function addpoin()
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $this->form_validation->set_rules('kode', 'kode', 'trim|prep_for_form');
        $this->form_validation->set_rules('nama', 'nama', 'trim|prep_for_form');
        $this->form_validation->set_rules('keterangan', 'keterangan', 'trim|prep_for_form');
        $this->form_validation->set_rules('poin', 'poin', 'trim|prep_for_form');
        $this->form_validation->set_rules('nilai', 'nilai', 'trim|prep_for_form');
        $this->form_validation->set_rules('isdriver', 'isdriver', 'trim|prep_for_form');
        $this->form_validation->set_rules('expire', 'expire', 'trim|prep_for_form');
        $this->form_validation->set_rules('status', 'status', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/poin/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);

            if ($this->upload->do_upload('image_poin')) {
                $gambar = html_escape($this->upload->data('file_name'));
            } else {
                $gambar = 'noimage.jpg';
            }

            $data             = [
                'image_poin'                       => $gambar,
                'kode'              => html_escape($this->input->post('kode', TRUE)),
                'nama'              => html_escape($this->input->post('nama', TRUE)),
                'keterangan'              => html_escape($this->input->post('keterangan', TRUE)),
                'poin'              => html_escape($this->input->post('poin', TRUE)),
                'nilai'              => html_escape($this->input->post('nilai', TRUE)),
                'isdriver'              => html_escape($this->input->post('isdriver', TRUE)),
                'expire'              => html_escape($this->input->post('expire', TRUE)),
                'status'                       => html_escape($this->input->post('status', TRUE)),
            ];
            $cekpromo = $this->poin->cekpoin($this->input->post('kode'));
                    if ($cekpromo->num_rows() > 0){
                    $this->session->set_flashdata('demo', 'Poin Sudah Ada');
                    redirect('poin/addpoin');
                    }else{
                    $this->poin->addpoin($data);
                    $this->session->set_flashdata('tambah', 'Poin Berhasil Ditambahkan');
                    redirect('poin');
            }
        } else {
            $this->load->view('includes/header', $data);
            $this->load->view('poin/addpoin', $data);
            $this->load->view('includes/footer');
        }
    }

    public function editpoin($id)

    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        
       $this->form_validation->set_rules('nama', 'nama', 'trim|prep_for_form');
        $this->form_validation->set_rules('kode', 'kode', 'trim|prep_for_form');
        $this->form_validation->set_rules('keterangan', 'keterangan', 'trim|prep_for_form');
        $this->form_validation->set_rules('poin', 'poin', 'trim|prep_for_form');
         $this->form_validation->set_rules('nilai', 'nilai', 'trim|prep_for_form');
        $this->form_validation->set_rules('isdriver', 'isdriver', 'trim|prep_for_form');
        $this->form_validation->set_rules('expire', 'expire', 'trim|prep_for_form');
        $this->form_validation->set_rules('status', 'status', 'trim|prep_for_form');
        $data['poin'] = $this->poin->getdatabyid($id)->row_array();
        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/poin/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = time();
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);

            if ($this->upload->do_upload('image_poin')) {
                unlink('images/poin/' . $this->poin->getdatabyid($id)->row('image_poin'));
                $gambar = html_escape($this->upload->data('file_name'));
                $datainsert             = [
                    'kode'                  => html_escape($this->input->post('kode', TRUE)),
                    'image_poin'                       => $gambar,
                    'nama'              => html_escape($this->input->post('nama', TRUE)),
                    'keterangan'              => html_escape($this->input->post('keterangan', TRUE)),
                    'poin'              => html_escape($this->input->post('poin', TRUE)),
                    'nilai'              => html_escape($this->input->post('nilai', TRUE)),
                    'isdriver'              => html_escape($this->input->post('isdriver', TRUE)),
                    'expire'              => html_escape($this->input->post('expire', TRUE)),
                    'status'                       => html_escape($this->input->post('status', TRUE)),
                ];
            } else {
                $datainsert             = [
                    'kode'                  => html_escape($this->input->post('kode', TRUE)),
                    'image_poin'                       => $gambar,
                    'nama'              => html_escape($this->input->post('nama', TRUE)),
                    'keterangan'              => html_escape($this->input->post('keterangan', TRUE)),
                    'poin'              => html_escape($this->input->post('poin', TRUE)),
                    'nilai'              => html_escape($this->input->post('nilai', TRUE)),
                    'isdriver'              => html_escape($this->input->post('isdriver', TRUE)),
                    'expire'              => html_escape($this->input->post('expire', TRUE)),
                    'status'                       => html_escape($this->input->post('status', TRUE)),
                ];
            }

            

            
            if (demo == TRUE) {
              $cekpromo = $this->poin->cekpoin($this->input->post('kode'));
                if ($cekpromo->num_rows() > 0 && $cekpromo->row_array()['kode'] != $this->input->post('kode')){
                    $this->session->set_flashdata('demo', 'Poin Sudah Ada');
                    $this->load->view('includes/header');
                    $this->load->view('poin/editpoin', $data);
                    $this->load->view('includes/footer');
                }else{
                $this->poin->editpoin($datainsert);
                $this->session->set_flashdata('tambah', 'Poin Berhasil Diubah');
                redirect('poin');
            }
            } else {
                $cekpromo = $this->poin->cekpoin($this->input->post('kode'));
                if ($cekpromo->num_rows() > 0 && $cekpromo->row_array()['kode'] != $this->input->post('kode')){
                    $this->session->set_flashdata('demo', 'Poin Sudah Ada');
                    $this->load->view('includes/header');
                    $this->load->view('poin/editpoin', $data);
                    $this->load->view('includes/footer');
                }else{
                $this->poin->editpoin($datainsert);
                $this->session->set_flashdata('tambah', 'Poin Berhasil Diubah');
                redirect('poin');
            }
            }
        } else {
            
            $this->load->view('includes/header', $data);
            $this->load->view('poin/editpoin', $data);
            $this->load->view('includes/footer');
        }
    }

    public function hapus($id)
    {
        if (demo == TRUE) {
         //   $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
         //   redirect('promocode/index');
          $data = $this->poin->getpoinbyid($id);

            if ($data['image_poin'] != 'noimage.jpg') {
                $gambar = $data['image_poin'];
                unlink('images/poin/' . $gambar);
            }

            $this->poin->hapuspoin($id);
            $this->session->set_flashdata('hapus', 'Poin Berhasil Dihapus');
            redirect('poin');
        } else {
            $data = $this->poin->getpoinbyid($id);

            if ($data['image_poin'] != 'noimage.jpg') {
                $gambar = $data['image_poin'];
                unlink('images/poin/' . $gambar);
            }

            $this->poin->hapuspoin($id);
            $this->session->set_flashdata('hapus', 'Poin Berhasil Dihapus');
            redirect('poin');
        }
    }
}