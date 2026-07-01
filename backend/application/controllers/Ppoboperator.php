<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Ppoboperator extends CI_Controller
{

    public function __construct()
    {
        parent::__construct();
        
        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('Group_model', 'group');

        $this->load->model('Ppoboperator_model', 'ppoboperator');
        $this->load->library('form_validation');
    }

    public function index()
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['ppoboperator'] = $this->ppoboperator->getallservice();
        $this->load->view('includes/header', $data);
        $this->load->view('ppoboperator/index', $data);
        $this->load->view('includes/footer');
    }

    public function ubah($id)
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $this->form_validation->set_rules('nama', 'nama', 'trim|prep_for_form');
        $this->form_validation->set_rules('kode', 'kode', 'trim|prep_for_form');
        $this->form_validation->set_rules('tipe', 'tipe', 'trim|prep_for_form');
        $this->form_validation->set_rules('status', 'status', 'trim|prep_for_form');

        $data = $this->ppoboperator->getfiturbyid($id);
        
        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();

        $id = html_escape($this->input->post('kode', TRUE));
        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/ppob/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);

            if ($this->upload->do_upload('ikon')) {
                if ($data['ikon'] != 'noimage.png') {
                    $gambar = $data['ikon'];
                    unlink('images/ppob/' . $gambar);
                }

                $gambar = html_escape($this->upload->data('file_name'));
            } else {
                $gambar = $data['ikon'];
            }
            $remove = array(".", ",");
            $add = array("", "");

            $data             = [
                'ikon'                          => $gambar,
                'kode'                         => html_escape($this->input->post('kode', TRUE)),
                'nama'                         => html_escape($this->input->post('nama', TRUE)),
                'tipe'                         => html_escape($this->input->post('tipe', TRUE)),
                'status'                        => html_escape($this->input->post('status', TRUE))
            ];

            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('ppoboperator/index');
            } else {

                $this->ppoboperator->ubahdatafitur($data);
                $this->session->set_flashdata('ubah', 'Ppob Has Been Changed');
                redirect('ppoboperator');
            }
        } else {

            $this->load->view('includes/header', $data);
            $this->load->view('ppoboperator/editservices' . $id, $data);
            $this->load->view('includes/footer');
        }
    }

    public function addservice()

    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $this->form_validation->set_rules('kode', 'kode', 'trim|prep_for_form');
        $this->form_validation->set_rules('nama', 'nama', 'trim|prep_for_form');
        $this->form_validation->set_rules('tipe', 'tipe', 'trim|prep_for_form');
        $this->form_validation->set_rules('ikon', 'ikon', 'trim|prep_for_form');
        $this->form_validation->set_rules('status', 'status', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/ppob/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);

            if ($this->upload->do_upload('ikon')) {
                $gambar = html_escape($this->upload->data('file_name'));
            } else {
                $gambar = 'noimage.png';
            }
            $data             = [
                'ikon'                          => $gambar,
                'kode'                         => html_escape($this->input->post('kode', TRUE)),
                'nama'                         => html_escape($this->input->post('nama', TRUE)),
                'tipe'              => html_escape($this->input->post('tipe', TRUE)),
                'status'                        => html_escape($this->input->post('status', TRUE))
            ];


            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('ppoboperator/index');
            } else {

                $this->ppoboperator->tambahdatafitur($data);
                $this->session->set_flashdata('ubah', 'PPOB Has Been Added');
                redirect('ppoboperator');
            }

        
        } else {

        $this->load->view('includes/header', $data);
        $this->load->view('ppoboperator/addservice');
        $this->load->view('includes/footer');
        }
    }

    public function hapusservice($id)
    {
        if (demo == TRUE) {
            $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
            redirect('services/index');
        } else {
            $data = $this->service->getfiturbyid($id);

            if ($data['icon'] != 'noimage.jpg') {
                $gambar = $data['icon'];
                unlink('images/fitur/' . $gambar);
            }

            $this->service->hapusserviceById($id);
            $this->session->set_flashdata('hapus', 'Service Has Been deleted');
            redirect('services');
        }
    }
}
