<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Donasi extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
        

        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('Donasi_model', 'donasi');
        $this->load->model('Group_model', 'group');
        $this->load->library('form_validation');
    }
    
    public function index()
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['donasi'] = $this->donasi->get_data_donasi_all();

        $this->load->view('includes/header', $data);
        $this->load->view('donasi/index', $data);
        $this->load->view('includes/footer');
    }
    
    public function tambah()

    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        
        $this->form_validation->set_rules('nama', 'nama', 'trim|prep_for_form');
        $this->form_validation->set_rules('alamat', 'alamat', 'trim|prep_for_form');
        $this->form_validation->set_rules('phone', 'phone', 'trim|prep_for_form');
        $this->form_validation->set_rules('judul', 'judul', 'trim|prep_for_form');
        $this->form_validation->set_rules('deskripsi', 'deskripsi', 'trim|prep_for_form');
        $this->form_validation->set_rules('tanggal_awal', 'tanggal_awal', 'trim|prep_for_form');
        $this->form_validation->set_rules('tanggal_akhir', 'tanggal_akhir', 'trim|prep_for_form');
        $this->form_validation->set_rules('status', 'status', 'trim|prep_for_form');
        
        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/promo/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);

            if ($this->upload->do_upload('foto')) {
                $gambar = html_escape($this->upload->data('file_name'));
            } else {
                $gambar = 'noimage.jpg';
            }
           

            $data = [
                'gambar'                => $gambar,
                'nama_lembaga'                 => html_escape($this->input->post('nama', TRUE)),
                'alamat'                 => html_escape($this->input->post('alamat', TRUE)),
                'phone'                 => html_escape($this->input->post('phone', TRUE)),
                'judul'                 => html_escape($this->input->post('judul', TRUE)),
                'deskripsi'             => html_escape($this->input->post('deskripsi', TRUE)),
                'tanggal_awal'          => html_escape($this->input->post('tanggal_awal', TRUE)),
                'tanggal_akhir'          => html_escape($this->input->post('tanggal_akhir', TRUE)),
                'status'                => html_escape($this->input->post('status', TRUE)),
                'regtime'                => date('Y-m-d H:i:s')
            ];
            
            // echo json_encode($data);die();
            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('donasi/tambah');
            } else {
                $save = $this->donasi->insert_data_donasi($data);
                if($save){
                    $this->session->set_flashdata('tambah', 'Data Has Been Added');
                }else{
                    $this->session->set_flashdata('error', 'Failed insert data');
                }
                
                redirect('donasi');
            }
        } else {
         
            $this->load->view('includes/header', $data);
            $this->load->view('donasi/adddonasi', $data);
            $this->load->view('includes/footer');
        }
    }
    
    public function ubah($id)
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');
        
        $this->form_validation->set_rules('nama', 'nama', 'trim|prep_for_form');
        $this->form_validation->set_rules('alamat', 'alamat', 'trim|prep_for_form');
        $this->form_validation->set_rules('phone', 'phone', 'trim|prep_for_form');
        $this->form_validation->set_rules('judul', 'judul', 'trim|prep_for_form');
        $this->form_validation->set_rules('deskripsi', 'deskripsi', 'trim|prep_for_form');
        $this->form_validation->set_rules('tanggal_awal', 'tanggal_awal', 'trim|prep_for_form');
        $this->form_validation->set_rules('tanggal_akhir', 'tanggal_akhir', 'trim|prep_for_form');
        $this->form_validation->set_rules('status', 'status', 'trim|prep_for_form');

        $data = $this->donasi->get_donasi_by_id($id);
        $id = html_escape($this->input->post('id', TRUE));
        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();

        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/promo/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);

            if ($this->upload->do_upload('foto')) {
                if ($data['gambar'] != 'noimage.jpg') {
                    $gambar = $data['gambar'];
                    unlink('images/promo/' . $gambar);
                }
                $gambar = html_escape($this->upload->data('file_name'));
            } else {
                $gambar = $data['gambar'];
            }

            $data             = [
                'id'                    => html_escape($this->input->post('id', TRUE)),
                'gambar'                => $gambar,
                'nama_lembaga'                 => html_escape($this->input->post('nama', TRUE)),
                'alamat'                 => html_escape($this->input->post('alamat', TRUE)),
                'phone'                 => html_escape($this->input->post('phone', TRUE)),
                'judul'                 => html_escape($this->input->post('judul', TRUE)),
                'deskripsi'             => html_escape($this->input->post('deskripsi', TRUE)),
                'tanggal_awal'          => html_escape($this->input->post('tanggal_awal', TRUE)),
                'tanggal_akhir'          => html_escape($this->input->post('tanggal_akhir', TRUE)),
                'status'                => html_escape($this->input->post('status', TRUE)),
            ];
            
            // echo json_encode($data);die();
            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('promoslider/index');
            } else {
                $upd = $this->donasi->update_data_donasi($data);
                if($upd){
                    $this->session->set_flashdata('ubah', 'Data Has Been Changed');
                }else{
                    $this->session->set_flashdata('error', 'Failed update data');
                }
                
                redirect('donasi');
            }
        } else {
            $this->load->view('includes/header', $data);
            $this->load->view('donasi/editdonasi' . $id, $data);
            $this->load->view('includes/footer');
        }
    }
    
    function detail($id)
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');
        $data = $this->donasi->get_donasi_by_id($id);
        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['total'] = $this->donasi->get_total_donasi($id);
        $data['donatur'] = $this->donasi->get_data_donatur($id);
        $data['wd'] = $this->donasi->get_data_withdraw($id);
        
        // echo json_encode($data);die();
        
        $this->load->view('includes/header', $data);
        $this->load->view('donasi/detail', $data);
        $this->load->view('includes/footer');
    }
    
    function withdraw()
    {
        $userId = $this->session->userdata('nama');
        $id = $this->input->post('id', TRUE);
        $jumlah = $this->input->post('jumlah', TRUE);
        $keterangan = $this->input->post('keterangan', TRUE);
        
        $config['upload_path']     = './images/promo/';
        $config['allowed_types'] = 'gif|jpg|png|jpeg';
        $config['max_size']         = '10000';
        $config['file_name']     = 'name';
        $config['encrypt_name']     = true;
        $this->load->library('upload', $config);

        if ($this->upload->do_upload('foto')) {
            $gambar = html_escape($this->upload->data('file_name'));
        } else {
            $gambar = 'noimage.jpg';
        }
        
        $data = [
            'id_donasi' => $id,
            'invoice' => 'WD-'.time(),
            'jumlah' => $jumlah,
            'keterangan' => $keterangan,
            'foto' => $gambar,
            'status' => 1,
            'regalias' => $userId,
            'regtime' => date('Y-m-d H:i:s')
            
        ];
        
        // echo json_encode($data);die();
        
        $upd = $this->donasi->insert_data_withdraw($data);
        if($upd){
            $this->session->set_flashdata('tambah', 'Data Has Been Added');
        }else{
            $this->session->set_flashdata('error', 'Failed update data');
        }
                
        redirect('donasi/detail/'. $id);
        
        
    }
    
    function hapus($id)
    {
        $data = $this->donasi->get_donasi_by_id($id);

        if ($data['gambar'] != 'noimage.jpg') {
            $gambar = $data['gambar'];
            unlink('images/promo/' . $gambar);
        }

        $this->donasi->delete_data_donasi($id);
        $this->session->set_flashdata('hapus', 'Data Has Been deleted');
        redirect('donasi');
    }
}