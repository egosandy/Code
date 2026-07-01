<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Group extends CI_Controller
{

    public function __construct()
    {
        parent::__construct();
        
        if ($this->session->userdata('username') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }

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
 
        $this->load->view('includes/header', $data);
        $this->load->view('group/index', $data);
        $this->load->view('includes/footer');
    }

    public function tambah()
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();

        $this->form_validation->set_rules('nama', 'nama', 'trim|prep_for_form');
        $this->form_validation->set_rules('keterangan', 'keterangan', 'trim|prep_for_form');
        if($this->form_validation->run() == TRUE){
            $menu_kode = $this->input->post('label', TRUE);

            $data = [
                'nama' => html_escape($this->input->post('nama', TRUE)),
                'akses' => '',
                'menu' => '',
                'keterangan' => html_escape($this->input->post('keterangan', TRUE))
            ];

            $savedata = $this->group->insert_data_group($data);
            if($savedata){
                $id = $this->db->insert_id();
                if(count($menu_kode) > 0){
                    foreach ($menu_kode as $value) {
                        $data_level[] = [
                            'menu_group' => $id,
                            'menu_kode' => $value,
                            'menu_set' => 1,
                            'menu_reg_date' => date('Y-m-d H:i:s'),
                            'menu_reg_alias' => $this->session->userdata('username') 
                        ];
                    }

                    $this->db->insert_batch('kode_menu_web', $data_level);
                }

                $this->session->set_flashdata('tambah', 'Group berhasil ditambahkan');
            }else{
                $this->session->set_flashdata('error', 'Group gagal ditambahkan');
            }

            redirect('group');


        }else{
            $this->load->view('includes/header', $data);
            $this->load->view('group/addgroup', $data);
            $this->load->view('includes/footer');
        }
    }

    public function ubah($id)
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data = $this->group->get_grooup_by_id($id);
        $id = html_escape($this->input->post('id', TRUE));

        $data_menu_user = $this->group->get_menu_user($groupLevel);
        $allmenu = $this->group->get_all_menu();

        $menu_checked = array();
        foreach ($data_menu_user as $value) {
            array_push($menu_checked, $value->menu_kode);
        }

        $data['menu'] = $data_menu_user;
        $data['allmenu'] = $allmenu;
        $data['menu_checked'] = $menu_checked;

        // echo json_encode($data);die();

        $this->form_validation->set_rules('nama', 'nama', 'trim|prep_for_form');
        $this->form_validation->set_rules('keterangan', 'keterangan', 'trim|prep_for_form');
        if($this->form_validation->run() == TRUE){
            $menu_kode = $this->input->post('label', TRUE);

            $data = [
                'id' => $id,
                'nama' => html_escape($this->input->post('nama', TRUE)),
                'akses' => '',
                'menu' => '',
                'keterangan' => html_escape($this->input->post('keterangan', TRUE))
            ];


            $update = $this->group->update_data_group($data);
            if($update){

                if(count($menu_kode) > 0){
                    $this->db->where('menu_group', $id);
                    $this->db->delete('kode_menu_web');

                    foreach ($menu_kode as $value) {
                        $data_level[] = [
                            'menu_group' => $id,
                            'menu_kode' => $value,
                            'menu_set' => 1,
                            'menu_reg_date' => date('Y-m-d H:i:s'),
                            'menu_reg_alias' => $this->session->userdata('username') 
                        ];
                    }

                    $this->db->insert_batch('kode_menu_web', $data_level);
                }

                $this->session->set_flashdata('ubah', 'Group berhasil diubah');
                redirect('group');
            }else{
                 $this->session->set_flashdata('error', 'Group gagal diubah');
                redirect('group');

            }

          

        }else{
            $this->load->view('includes/header', $data);
            $this->load->view('group/editgroup'. $id, $data);
            $this->load->view('includes/footer');
        }

    }

   
}
