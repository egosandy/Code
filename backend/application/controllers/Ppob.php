<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Ppob extends CI_Controller
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

    public function index()
    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $data['ppob'] = $this->ppob->getAllOperator();

        $this->load->view('includes/header');
        $this->load->view('ppob/index',$data);
        $this->load->view('includes/footer');
    }

    public function addpromocode()

    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();

        $this->form_validation->set_rules('nama_promo', 'nama_promo', 'trim|prep_for_form');
        $this->form_validation->set_rules('kode_promo', 'kode_promo', 'trim|prep_for_form');
        $this->form_validation->set_rules('point', 'point', 'trim|prep_for_form');
        $this->form_validation->set_rules('nominal_promo', 'nominal_promo', 'trim|prep_for_form');
        $this->form_validation->set_rules('type_promo', 'type_promo', 'trim|prep_for_form');
        $this->form_validation->set_rules('minimal', 'minimal', 'trim|prep_for_form');
        $this->form_validation->set_rules('fitur', 'fitur', 'trim|prep_for_form');
        $this->form_validation->set_rules('status', 'status', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/promo/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);

            if ($this->upload->do_upload('image_promo')) {
                $gambar = html_escape($this->upload->data('file_name'));
            } else {
                $gambar = 'noimage.jpg';
            }

            if ($this->input->post('type_promo') == 'persen'){
                $nominal = html_escape($this->input->post('nominal_promo_persen', TRUE));
            } else if ($this->input->post('type_promo') == 'fix'){
               $nominal = html_escape($this->input->post('nominal_promo_persen', TRUE));
            } else if ($this->input->post('type_promo') == 'point'){
              $nominal = html_escape($this->input->post('nominal_promo_persen', TRUE));
            }else{
              $nominal = html_escape($this->input->post('nominal_promo_persen', TRUE));
            }

            $data             = [
                'image_promo'                       => $gambar,
                'nama_promo'              => html_escape($this->input->post('nama_promo', TRUE)),
                'kode_promo'              => html_escape($this->input->post('kode_promo', TRUE)),
                'point'              => html_escape($this->input->post('point', TRUE)),
                'nominal_promo'              => $nominal,
                'type_promo'              => html_escape($this->input->post('type_promo', TRUE)),
                'minimal'              => html_escape($this->input->post('minimal', TRUE)),
                'expired'              => html_escape($this->input->post('expired', TRUE)),
                'fitur'                  => html_escape($this->input->post('fitur', TRUE)),
                'status'                       => html_escape($this->input->post('status', TRUE)),
            ];
            if (demo == TRUE) {
             //   $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
              //  redirect('promocode/addpromocode');
               $cekpromo = $this->promocode->cekpromo($this->input->post('kode_promo'));
                if ($cekpromo->num_rows() > 0){

                    $this->session->set_flashdata('demo', 'Promotion code already exist');
                    redirect('promocode/addpromocode');
                }else{
                $this->promocode->addpromocode($data);
                $this->session->set_flashdata('tambah', 'Promotion Slider Has Been Added');
                redirect('promocode');
            }
            } else {
                $cekpromo = $this->promocode->cekpromo($this->input->post('kode_promo'));
                if ($cekpromo->num_rows() > 0){

                    $this->session->set_flashdata('demo', 'Promotion code already exist');
                    redirect('promocode/addpromocode');
                }else{
                $this->promocode->addpromocode($data);
                $this->session->set_flashdata('tambah', 'Promotion Slider Has Been Added');
                redirect('promocode');
            }
            }
        } else {
            $data['fitur'] = $this->fitur->getallservice();
            $this->load->view('includes/header', $data);
            $this->load->view('promocode/addpromocode', $data);
            $this->load->view('includes/footer');
        }
    }

    public function editpromocode($id)

    {
        $groupLevel = $this->session->userdata('role');
        $userId = $this->session->userdata('id');

        $data['menu'] = $this->group->get_menu_user($groupLevel);
        $data['allmenu'] = $this->group->get_all_menu();
        $this->form_validation->set_rules('nama_promo', 'nama_promo', 'trim|prep_for_form');
        $this->form_validation->set_rules('kode_promo', 'kode_promo', 'trim|prep_for_form');
        $this->form_validation->set_rules('point', 'point', 'trim|prep_for_form');
        $this->form_validation->set_rules('nominal_promo', 'nominal_promo', 'trim|prep_for_form');
        $this->form_validation->set_rules('type_promo', 'type_promo', 'trim|prep_for_form');
        $this->form_validation->set_rules('minimal', 'minimal', 'trim|prep_for_form');
        $this->form_validation->set_rules('fitur', 'fitur', 'trim|prep_for_form');
        $this->form_validation->set_rules('status', 'status', 'trim|prep_for_form');
        $data['promo'] = $this->promocode->getpromobyid($id)->row_array();
        $data['fitur'] = $this->fitur->getallservice();
        
        if ($this->form_validation->run() == TRUE) {
            if ($this->input->post('type_promo') == 'persen'){
                $nominal = html_escape($this->input->post('nominal_promo_persen', TRUE));
            } else {
                $nominal = str_replace(".","",html_escape($this->input->post('nominal_promo', TRUE)));
            }

            $config['upload_path']     = './images/promo/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = time();
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);

            if ($this->upload->do_upload('image_promo')) {
                unlink('images/promo/' . $this->promocode->getpromobyid($id)->row('image_promo'));
                $gambar = html_escape($this->upload->data('file_name'));
                $datainsert             = [
                    'id_promo'                  => html_escape($this->input->post('id_promo', TRUE)),
                    'image_promo'                       => $gambar,
                    'nama_promo'              => html_escape($this->input->post('nama_promo', TRUE)),
                    'point'              => html_escape($this->input->post('point', TRUE)),
                    'kode_promo'              => html_escape($this->input->post('kode_promo', TRUE)),
                    'nominal_promo'              => $nominal,
                    'type_promo'              => html_escape($this->input->post('type_promo', TRUE)),
                    'minimal'              => html_escape($this->input->post('minimal', TRUE)),
                    'expired'              => html_escape($this->input->post('expired', TRUE)),
                    'fitur'                  => html_escape($this->input->post('fitur', TRUE)),
                    'status'                       => html_escape($this->input->post('status', TRUE)),
                ];
            } else {
                $datainsert             = [
                    'id_promo'                  => html_escape($this->input->post('id_promo', TRUE)),
                    'nama_promo'              => html_escape($this->input->post('nama_promo', TRUE)),
                    'kode_promo'              => html_escape($this->input->post('kode_promo', TRUE)),
                    'point'              => html_escape($this->input->post('point', TRUE)),
                    'nominal_promo'              => $nominal,
                    'type_promo'              => html_escape($this->input->post('type_promo', TRUE)),
                    'minimal'              => html_escape($this->input->post('minimal', TRUE)),
                    'expired'              => html_escape($this->input->post('expired', TRUE)),
                    'fitur'                  => html_escape($this->input->post('fitur', TRUE)),
                    'status'                       => html_escape($this->input->post('status', TRUE)),
                ];
            }

            

            
            if (demo == TRUE) {
              //  $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
              //  $this->load->view('includes/header');
              //  $this->load->view('promocode/editpromocode', $data);
              //  $this->load->view('includes/footer');
              $cekpromo = $this->promocode->cekpromo($this->input->post('kode_promo'));
                if ($cekpromo->num_rows() > 0 && $cekpromo->row_array()['id_promo'] != $this->input->post('id_promo')){
                    $this->session->set_flashdata('demo', 'Promotion code already exist');
                    $this->load->view('includes/header');
                    $this->load->view('promocode/editpromocode', $data);
                    $this->load->view('includes/footer');
                }else{
                $this->promocode->editpromocode($datainsert);
                $this->session->set_flashdata('tambah', 'Promotion code Has Been Changed');
                redirect('promocode');
            }
            } else {
                $cekpromo = $this->promocode->cekpromo($this->input->post('kode_promo'));
                if ($cekpromo->num_rows() > 0 && $cekpromo->row_array()['id_promo'] != $this->input->post('id_promo')){
                    $this->session->set_flashdata('demo', 'Promotion code already exist');
                    $this->load->view('includes/header');
                    $this->load->view('promocode/editpromocode', $data);
                    $this->load->view('includes/footer');
                }else{
                $this->promocode->editpromocode($datainsert);
                $this->session->set_flashdata('tambah', 'Promotion code Has Been Changed');
                redirect('promocode');
            }
            }
        } else {
            
            $this->load->view('includes/header', $data);
            $this->load->view('promocode/editpromocode', $data);
            $this->load->view('includes/footer');
        }
    }

    public function hapus($id)
    {
        if (demo == TRUE) {
         //   $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
         //   redirect('promocode/index');
          $data = $this->promocode->getpromocodeById($id);

            if ($data['image_promo'] != 'noimage.jpg') {
                $gambar = $data['image_promo'];
                unlink('images/promo/' . $gambar);
            }

            $this->promocode->hapuspromocodeById($id);
            $this->session->set_flashdata('hapus', 'Promo Code Has Been deleted');
            redirect('promocode');
        } else {
            $data = $this->promocode->getpromocodeById($id);

            if ($data['image_promo'] != 'noimage.jpg') {
                $gambar = $data['image_promo'];
                unlink('images/promo/' . $gambar);
            }

            $this->promocode->hapuspromocodeById($id);
            $this->session->set_flashdata('hapus', 'Promo Code Has Been deleted');
            redirect('promocode');
        }
    }
}