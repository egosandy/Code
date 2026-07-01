<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Metode extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
        
        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('Group_model', 'group');

        $this->load->model('Payment_model', 'payment');
        $this->load->library('form_validation');
    }
    
    public function index()
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['payment'] = $this->payment->get_data_payment_method();
        $this->load->view('includes/header', $data);
        $this->load->view('payment/index', $data);
        $this->load->view('includes/footer');
    }
    
    public function tambah()
    {
        
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');
        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $this->form_validation->set_rules('keterangan', 'keterangan', 'trim|prep_for_form');
        $this->form_validation->set_rules('nama', 'nama', 'trim|prep_for_form');
        $this->form_validation->set_rules('tipe', 'tipe', 'trim|prep_for_form');
        $this->form_validation->set_rules('status', 'status', 'trim|prep_for_form');
        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/promo/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);

            if ($this->upload->do_upload('logo')) {
                $gambar = html_escape($this->upload->data('file_name'));
            } else {
                $gambar = 'noimage.png';
            }
            $tipe = html_escape($this->input->post('tipe', TRUE));
            $jenis = html_escape($this->input->post('jenis', TRUE));
            $nama = html_escape($this->input->post('nama', TRUE));
            $keterangan = html_escape($this->input->post('keterangan', TRUE));
            $biaya = html_escape($this->input->post('biaya', TRUE));
            $channel = html_escape($this->input->post('channel_code', TRUE));
            $bank = html_escape($this->input->post('nama_bank', TRUE));
            $rekening = html_escape($this->input->post('no_rekening', TRUE));
            $atasnama = html_escape($this->input->post('nama_rekening', TRUE));
            
            $data = [
                'tipe' => $tipe,
                'jenis' =>  $tipe == '1' ? $jenis : 0,
                'nama' => $nama,
                'keterangan' => $keterangan,
                'bank' => $bank,
                'no_rekening' => $rekening,
                'nama_rekening' => $atasnama,
                'image' => $gambar,
                'channel_code' => $channel,
                'status' =>  html_escape($this->input->post('status', TRUE)),
                'regtime' => date('Y-m-d H:i:s')
            ];
            
            $this->payment->insert_data_payment($data);
            $this->session->set_flashdata('ubah', 'Data Has Been Added');
            redirect('metode');
            
        }else{
            $this->load->view('includes/header', $data);
            $this->load->view('payment/addpayment');
            $this->load->view('includes/footer');
        }
    }
    
    public function ubah($id)
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');
        $data = $this->payment->get_data_payment_by_id($id);
        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $this->form_validation->set_rules('keterangan', 'keterangan', 'trim|prep_for_form');
        $this->form_validation->set_rules('nama', 'nama', 'trim|prep_for_form');
        $this->form_validation->set_rules('tipe', 'tipe', 'trim|prep_for_form');
        $this->form_validation->set_rules('status', 'status', 'trim|prep_for_form');
        $id = html_escape($this->input->post('id', TRUE));
        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/promo/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);

            if ($this->upload->do_upload('logo')) {
                if ($data['image'] != 'noimage.png') {
                    $gambar = $data['image'];
                    unlink('images/promo/' . $gambar);
                }

                $gambar = html_escape($this->upload->data('file_name'));
            } else {
                $gambar = $data['image'];
            }
            
            $tipe = html_escape($this->input->post('tipe', TRUE));
            $jenis = html_escape($this->input->post('jenis', TRUE));
            $nama = html_escape($this->input->post('nama', TRUE));
            $keterangan = html_escape($this->input->post('keterangan', TRUE));
            $biaya = html_escape($this->input->post('biaya', TRUE));
            $channel = html_escape($this->input->post('channel_code', TRUE));
            $bank = html_escape($this->input->post('nama_bank', TRUE));
            $rekening = html_escape($this->input->post('no_rekening', TRUE));
            $atasnama = html_escape($this->input->post('nama_rekening', TRUE));
            
            $data = [
                'id' =>  $id,
                'tipe' => $tipe,
                'jenis' =>  $tipe == '1' ? $jenis : 0,
                'nama' => $nama,
                'keterangan' => $keterangan,
                'bank' => $bank,
                'no_rekening' => $rekening,
                'nama_rekening' => $atasnama,
                'image' => $gambar,
                'channel_code' => $channel,
                'status' =>  html_escape($this->input->post('status', TRUE)),
            ];
            // echo json_encode($data);die();
            $this->payment->update_data_payment($data);
            $this->session->set_flashdata('ubah', 'Data Has Been Edited');
            redirect('metode');
            
            
        }else{
            $this->load->view('includes/header', $data);
            $this->load->view('payment/editpayment' . $id, $data);
            $this->load->view('includes/footer');
        }
    }
    
}