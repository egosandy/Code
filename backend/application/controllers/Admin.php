<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Admin extends CI_Controller
{

    public function __construct()
    {
        parent::__construct();
       
        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('profile_model', 'profile');
        $this->load->model('Group_model', 'group');
        $this->load->library('form_validation');
    }

    public function index()
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        
        $data['admin'] = $this->profile->get_admin_all();
        $this->load->view('includes/header', $data);
        $this->load->view('admin/index', $data);
        $this->load->view('includes/footer');
    }

    
    public function tambah()
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        
        $this->form_validation->set_rules('username', 'username', 'trim|prep_for_form');
        $this->form_validation->set_rules('nama', 'nama', 'trim|prep_for_form');
        $this->form_validation->set_rules('email', 'email', 'trim|prep_for_form');
        $this->form_validation->set_rules('password', 'password', 'trim|prep_for_form');
        $this->form_validation->set_rules('status', 'status', 'trim|prep_for_form');
        $data['level'] = $this->profile->get_level();
        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/admin/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);

            if ($this->upload->do_upload('foto')) {
                $gambar = html_escape($this->upload->data('file_name'));
            } else {
                $gambar = 'noimage.png';
            }
            
            $check = $this->profile->check_user_exist(html_escape($this->input->post('username', TRUE)));
            if($check){
                $this->session->set_flashdata('error', 'Username exist, try another!');
                redirect('admin/tambah');
            }else{
                $data  = [
                    'image'     => $gambar,
                    'user_name' => html_escape($this->input->post('username', TRUE)),
                    'nama'      => html_escape($this->input->post('nama', TRUE)),
                    'email'     => html_escape($this->input->post('email', TRUE)),
                    'password'  => html_escape(sha1($this->input->post('password', TRUE))),
                    'level'     => html_escape($this->input->post('id_level', TRUE)),
                    'status'    => html_escape($this->input->post('status', TRUE))
                ];
    
    
                if (demo == TRUE) {
                    $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                    redirect('admin');
                } else {
    
                    $result = $this->profile->add_data_admin($data);
                    // echo json_encode($data);die();
                    $this->session->set_flashdata('ubah', 'User admin Has Been Added');
                    redirect('admin');
                }
                
            }
        
        } else {
            
            $this->load->view('includes/header', $data);
            $this->load->view('admin/addadmin', $data);
            $this->load->view('includes/footer');
        }
    }
    
    public function ubah($id)
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');
        $this->form_validation->set_rules('user_name', 'user_name', 'trim|prep_for_form');
        $this->form_validation->set_rules('email', 'email', 'trim|prep_for_form');
        $this->form_validation->set_rules('username', 'username', 'trim|prep_for_form');
        $this->form_validation->set_rules('nama', 'nama', 'trim|prep_for_form');
        $this->form_validation->set_rules('email', 'email', 'trim|prep_for_form');
        $this->form_validation->set_rules('password', 'password', 'trim|prep_for_form');
        $this->form_validation->set_rules('status', 'status', 'trim|prep_for_form');
       
        $data = $this->profile->get_admin_by_id($id);
        $id =  html_escape($this->input->post('id', TRUE));
        $data['level'] = $this->profile->get_level();
        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();

        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/admin/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);

            if ($this->upload->do_upload('image')) {
                if ($data['image'] != 'noimage.jpg') {
                    $image = $data['image'];
                    unlink('images/admin/' . $image);
                }
                $gambar = html_escape($this->upload->data('file_name'));
            } else {
                $gambar = $data['image'];
            }

            if ($this->input->post('password', TRUE) == NULL) {
                $pass = $data['password'];
            } else {
                $pass = html_escape(sha1($this->input->post('password', TRUE)));
            }
             $data  = [
                'id'       => html_escape($this->input->post('id', TRUE)),
                'image'     => $gambar,
                'user_name' => html_escape($this->input->post('username', TRUE)),
                'nama'      => html_escape($this->input->post('nama', TRUE)),
                'email'     => html_escape($this->input->post('email', TRUE)),
                'password'  => $pass,
                'level'     => html_escape($this->input->post('id_level', TRUE)),
                'status'    => html_escape($this->input->post('status', TRUE))
            ];

            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('profile/index');
            } else {
                $this->profile->update_data_admin($data);
                $this->session->set_flashdata('ubah', 'Data Has Been Changed');
                redirect('admin');
            }
        } else {
            // echo json_encode($data);die();
            $this->load->view('includes/header', $data);
            $this->load->view('admin/editadmin' . $id, $data);
            $this->load->view('includes/footer');
        }
    }
    
    public function hapus($id)
    {
        if (demo == TRUE) {
            $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
            redirect('admin/index');
        } else {
            $data = $this->profile->get_admin_by_id($id);

            if ($data['icon'] != 'noimage.jpg') {
                $gambar = $data['image'];
                unlink('images/admin/' . $gambar);
            }

            $this->profile->delete_data_admin($id);
            $this->session->set_flashdata('hapus', 'Data Has Been deleted');
            redirect('admin');
        }
    }
}
