<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Digi extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
        

        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('Group_model', 'group');
        $this->load->model('Ppob_model', 'ppob');
        $this->load->library('form_validation');
    }
    
    function index()
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['transaksi'] = $this->ppob->get_data_transaksi();
        // echo json_encode($data);die();
        $this->load->view('includes/header', $data);
        $this->load->view('digi/index',$data);
        $this->load->view('includes/footer');
    }
    
    function kategori()
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['produk'] = $this->ppob->get_all_kategori_produk();
        // echo json_encode($data);die();
        $this->load->view('includes/header', $data);
        $this->load->view('digi/kategori',$data);
        $this->load->view('includes/footer');
    }
    
    function addcat()
    {
        $nama = html_escape($this->input->post('nama', TRUE));
        $tipe = html_escape($this->input->post('tipe', TRUE));
        $status = html_escape($this->input->post('status', TRUE));
        
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
        
        $data = [
            'nama' => $nama,
            'tipe' => $tipe,
            'icon' => base_url().'images/ppob/'.$gambar,
            'status' => $status
        ];
        
        $savedata = $this->ppob->insert_kategori($data);
        if($savedata){
            $this->session->set_flashdata('tambah', 'Berhasil menambahkan data');
        }else{
            $this->session->set_flashdata('error', 'Gagal menambahkan data');
        }
        redirect('digi/kategori');
        
    }
    
    function editcat()
    {
        $id = html_escape($this->input->post('id', TRUE));
        $nama = html_escape($this->input->post('nama', TRUE));
        $tipe = html_escape($this->input->post('tipe', TRUE));
        $status = html_escape($this->input->post('status', TRUE));
        
        $row = $this->ppob->get_kategori_by_id($id);
        
        $config['upload_path']     = './images/ppob/';
        $config['allowed_types'] = 'gif|jpg|png|jpeg';
        $config['max_size']         = '10000';
        $config['file_name']     = 'name';
        $config['encrypt_name']     = true;
        $this->load->library('upload', $config);

        if ($this->upload->do_upload('ikon')) {
            if ($row['foto'] != 'noimage.jpg') {
                $gambar = $row['icon'];
                unlink('images/ppob/' . $gambar);
            }
            $gambar = html_escape($this->upload->data('file_name'));
        } else {
            $gambar = $row['icon'];
        }    
        
        $data = [
            'id' => $id,
            'nama' => $nama,
            'tipe' => $tipe,
            'icon' => base_url().'images/ppob/'.$gambar,
            'status' => $status
        ];
        
        $savedata = $this->ppob->update_kategori($data);
        if($savedata){
            $this->session->set_flashdata('tambah', 'Berhasil ubah data');
        }else{
            $this->session->set_flashdata('error', 'Gagal ubah data');
        }
        redirect('digi/kategori');
    }
    
    function delcat($id)
    {
        $deldata = $this->ppob->delete_kategori($id);
        $this->session->set_flashdata('hapus', 'Berhasil hapus data');
         redirect('digi/kategori');
    }
    
    function operator(){
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['produk'] = $this->ppob->get_all_kategori_produk();
        $data['operator'] = $this->ppob->get_all_operator();
        // echo json_encode($data);die();
        $this->load->view('includes/header', $data);
        $this->load->view('digi/operator',$data);
        $this->load->view('includes/footer');
    }
    
    function addops()
    {
        $nama = html_escape($this->input->post('nama', TRUE));
        $kategori = html_escape($this->input->post('kategori', TRUE));
        $status = html_escape($this->input->post('status', TRUE));
        
        // $config['upload_path']     = './images/ppob/';
        // $config['allowed_types'] = 'gif|jpg|png|jpeg';
        // $config['max_size']         = '10000';
        // $config['file_name']     = 'name';
        // $config['encrypt_name']     = true;
        // $this->load->library('upload', $config);

        // if ($this->upload->do_upload('ikon')) {
        //     $gambar = html_escape($this->upload->data('file_name'));
        // } else {
        //     $gambar = 'noimage.png';
        // }
        
        $data = [
            'nama' => $nama,
            'kategori' => $kategori,
            'status' => $status
        ];
        // echo json_encode($data);die();
        $savedata = $this->ppob->insert_operator($data);
        
        if($savedata){
            $this->session->set_flashdata('tambah', 'Berhasil menambahkan data');
        }else{
            $this->session->set_flashdata('error', 'Gagal menambahkan data');
        }
        redirect('digi/operator');
        
    }
    
    function editops()
    {
        $id = html_escape($this->input->post('id', TRUE));
        $nama = html_escape($this->input->post('nama', TRUE));
        $kategori = html_escape($this->input->post('kategori', TRUE));
        $status = html_escape($this->input->post('status', TRUE));
        
        $data = [
            'id' => $id,
            'nama' => $nama,
            'kategori' => $kategori,
            'status' => $status
        ];
        
        $savedata = $this->ppob->update_operator($data);
        if($savedata){
            $this->session->set_flashdata('tambah', 'Berhasil ubah data');
        }else{
            $this->session->set_flashdata('error', 'Gagal ubah data');
        }
        redirect('digi/operator');
    }
    
    function delops($id)
    {
        $deldata = $this->ppob->delete_operator($id);
        $this->session->set_flashdata('hapus', 'Berhasil hapus data');
         redirect('digi/operator');
    }
    
    
    function produk(){
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['produk'] = $this->ppob->get_all_produk();
        $data['operator'] = $this->ppob->get_all_operator();
        // echo json_encode($data);die();
        $this->load->view('includes/header', $data);
        $this->load->view('digi/produk',$data);
        $this->load->view('includes/footer');
    }
    
    function addproduk()
    {
        $kode = html_escape($this->input->post('kode', TRUE));
        $nama = html_escape($this->input->post('nama', TRUE));
        $deskripsi = html_escape($this->input->post('deskripsi', TRUE));
        $hpp = html_escape($this->input->post('hpp', TRUE));
        $harga = html_escape($this->input->post('harga', TRUE));
        $operator = html_escape($this->input->post('operator', TRUE));
        $status = html_escape($this->input->post('status', TRUE));
        
        $data = [
            'kode' => $kode,
            'nama' => $nama,
            'deskripsi' => $deskripsi,
            'operator' => $operator,
            'hpp' => $hpp,
            'harga' => $harga,
            'status' => $status
        ];
      
        $savedata = $this->ppob->insert_produk($data);
        
        if($savedata){
            $this->session->set_flashdata('tambah', 'Berhasil menambahkan data');
        }else{
            $this->session->set_flashdata('error', 'Gagal menambahkan data');
        }
        redirect('digi/produk');
        
    }
    
     function editproduk()
    {
        $id = html_escape($this->input->post('id', TRUE));
        $kode = html_escape($this->input->post('kode', TRUE));
        $nama = html_escape($this->input->post('nama', TRUE));
        $deskripsi = html_escape($this->input->post('deskripsi', TRUE));
        $hpp = html_escape($this->input->post('hpp', TRUE));
        $harga = html_escape($this->input->post('harga', TRUE));
        $operator = html_escape($this->input->post('operator', TRUE));
        $status = html_escape($this->input->post('status', TRUE));
        
        $data = [
            'id' => $id,
            'kode' => $kode,
            'nama' => $nama,
            'deskripsi' => $deskripsi,
            'operator' => $operator,
            'hpp' => $hpp,
            'harga' => $harga,
            'status' => $status
        ];
        
        $savedata = $this->ppob->update_produk($data);
        if($savedata){
            $this->session->set_flashdata('tambah', 'Berhasil ubah data');
        }else{
            $this->session->set_flashdata('error', 'Gagal ubah data');
        }
        redirect('digi/produk');
    }
    
    function delproduk($id)
    {
        $deldata = $this->ppob->delete_produk($id);
        $this->session->set_flashdata('hapus', 'Berhasil hapus data');
         redirect('digi/produk');
    }
}