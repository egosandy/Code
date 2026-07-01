<?php


class Appsettings_model extends CI_model
{
    public function getappbyid()
    {
        return $this->db->get_where('app_settings', ['id' => '1'])->row_array();
    }

    public function gettransfer()
    {
        $this->db->select('*');
        $this->db->from('list_bank');
        return $this->db->get()->result_array();
    }

    public function getbankid($id)
    {
        $this->db->select('*');
        $this->db->from('list_bank');
        $this->db->where('id_bank', $id);
        return $this->db->get()->row_array();
    }

    public function ubahdataappsettings($data)
    {
        $this->db->set('app_logo', $data['app_logo']);
        $this->db->set('app_email', $data['app_email']);
        $this->db->set('app_website', $data['app_website']);

        $this->db->set('app_privacy_policy', $data['app_privacy_policy']);
        $this->db->set('app_aboutus', $data['app_aboutus']);
        $this->db->set('app_address', $data['app_address']);
        $this->db->set('app_name', $data['app_name']);
        $this->db->set('app_contact', $data['app_contact']);
        $this->db->set('app_linkgoogle', $data['app_linkgoogle']);
        $this->db->set('app_currency', $data['app_currency']);
        $this->db->set('map_key', $data['map_key']);
        $this->db->set('maintenance', $data['maintenance']);
        $this->db->set('main_background', $data['main_background']);
        $this->db->set('saldo_background', $data['saldo_background']);
        $this->db->set('isotp', $data['isotp']);
        $this->db->set('sender_wasap', $data['sender_wasap']);
        $this->db->set('key_api_wasap', $data['key_api_wasap']);
        $this->db->set('versi_cs', $data['versi_cs']);
        $this->db->set('versi_driver', $data['versi_driver']);
        $this->db->set('versi_mitra', $data['versi_mitra']);
        $this->db->set('force_update_user', $data['force_update_user']);
        $this->db->set('force_update_driver', $data['force_update_driver']);
        $this->db->set('force_update_mitra', $data['force_update_mitra']);
        $this->db->set('fee_rain', $data['fee_rain']);
        $this->db->set('fee_rain_status', $data['fee_rain_status']);
        $this->db->set('fee_time_status', $data['fee_time_status']);
        $this->db->set('fee_time_on', $data['fee_time_on']);
        $this->db->set('fee_time_off', $data['fee_time_off']);
        $this->db->set('fee_add_time', $data['fee_add_time']);
        $this->db->where('id', '1');
        $this->db->update('app_settings', $data);
    }

    public function ubahdatarekening($data, $id)
    {
        $this->db->where('id_bank', $id);
        $this->db->update('list_bank', $data);
    }

    public function hapusrekening($id)
    {
        $this->db->where('id_bank', $id);
        $this->db->delete('list_bank');
    }

    public function adddatarekening($data)
    {
        $this->db->insert('list_bank', $data);
    }

    public function ubahdataemail($data)
    {
        $this->db->set('email_subject', $data['email_subject']);
        $this->db->set('email_text1', $data['email_text1']);
        $this->db->set('email_text2', $data['email_text2']);
        $this->db->set('email_subject_confirm', $data['email_subject_confirm']);
        $this->db->set('email_text3', $data['email_text3']);
        $this->db->set('email_text4', $data['email_text4']);

        $this->db->where('id', '1');
        $this->db->update('app_settings', $data);
    }

    public function ubahdatasmtp($data)
    {
        $this->db->set('smtp_host', $data['smtp_host']);
        $this->db->set('smtp_port', $data['smtp_port']);
        $this->db->set('smtp_username', $data['smtp_username']);
        $this->db->set('smtp_password', $data['smtp_password']);
        $this->db->set('smtp_from', $data['smtp_from']);
        $this->db->set('smtp_secure', $data['smtp_secure']);

        $this->db->where('id', '1');
        $this->db->update('app_settings', $data);
    }

    public function ubahmidtrans($data)
    {
        $this->db->set('midtrans_url', $data['midtrans_url']);
        $this->db->set('midtrans_key', $data['midtrans_key']);
        $this->db->set('midtrans_aktif', $data['midtrans_aktif']);

        $this->db->where('id', '1');
        $this->db->update('app_settings', $data);
    }
    public function ubahmobilepulsa($data)
    {
        $this->db->set('mobilepulsa_url', $data['mobilepulsa_url']);
        $this->db->set('mobilepulsa_user', $data['mobilepulsa_user']);
        $this->db->set('mobilepulsa_pass', $data['mobilepulsa_pass']);
        $this->db->set('mobilepulsa_aktif', $data['mobilepulsa_aktif']);
        $this->db->where('id', '1');
        $this->db->update('app_settings', $data);
    }
    public function ubahbanner($data)
    {
        $this->db->set('bannerid', $data['bannerid']);
        $this->db->set('bannerunit', $data['bannerunit']);
        $this->db->set('banneraktif', $data['banneraktif']);
        $this->db->where('id', '1');
        $this->db->update('app_settings', $data);
    }
}
