{include file="rad-template-header.tpl"} <!-- DON'T REMOVE THIS LINE -->
{foreach $v as $vs} <!-- DON'T REMOVE THIS LINE -->
<!-- don't include opening body tag -->
<!-- START CUSTOM HTML -->
<!-- ############################# -->

{if $vs@first}
<style type="text/css">
/* ===== Ukuran kertas: HVS F4 / Folio (215mm x 330mm) ===== */
/* margin 7mm aman utk printer yg membaca F4 sbg 210mm maupun 215mm */
@page { size: 215mm 330mm; margin: 7mm; }

html, body { margin: 0; padding: 0; }

/* Lembar penampung: font-size 0 utk menghilangkan spasi antar kartu
   sehingga 5 voucher pas berjajar dlm 1 baris (5 x 39mm = 195mm). */
.vsheet { font-size: 0; line-height: 0; }

/* ===== Kartu voucher: 39mm x 30mm (rasio ~1:1.3, persegi panjang) =====
   5 kolom x 10 baris = 50 voucher / halaman -> 100 voucher = 2 lembar F4. */
.voucher {
  display: inline-block;
  vertical-align: top;
  width: 39mm;
  height: 30mm;
  margin: 0;
  padding: 0;
  overflow: hidden;
  box-sizing: border-box;
  font-family: Arial, Helvetica, "Segoe UI", sans-serif;
  font-size: 8pt;
  color: #222;
  background: #fff;
  page-break-inside: avoid;
  break-inside: avoid;
  -webkit-print-color-adjust: exact;
  print-color-adjust: exact;
}
.voucher * { box-sizing: border-box; line-height: 1.12; }

.v-card { display: flex; flex-direction: column; height: 100%; border: 0.4mm solid #444; }

.v-strip { flex: 0 0 auto; height: 1.4mm; }

.v-body { flex: 1 1 auto; display: flex; flex-direction: column; padding: 1mm 1.3mm; }

.v-head { display: flex; align-items: center; justify-content: space-between; }
.v-logo { max-width: 17mm; max-height: 5mm; }
.v-logo img { max-width: 17mm; max-height: 5mm; width: auto; height: auto; display: block; }
.v-price { font-size: 12.5pt; font-weight: bold; white-space: nowrap; }
.v-price .cur { font-size: 5pt; font-weight: bold; vertical-align: top; margin-right: 0.3mm; }

.v-mid { flex: 1 1 auto; display: flex; gap: 1mm; margin-top: 0.8mm; }
.v-info { flex: 1 1 auto; min-width: 0; }
.v-label {
  font-size: 4.6pt; font-weight: bold; color: #666;
  letter-spacing: 0.2px; text-transform: uppercase;
  border-bottom: 0.3mm solid #ddd; padding-bottom: 0.3mm;
}
.v-code { font-size: 11.5pt; font-weight: bold; word-break: break-all; margin: 0.5mm 0; }
.v-code-sm { font-size: 8pt; font-weight: bold; word-break: break-all; margin: 0.5mm 0; }
.v-meta { font-size: 4.9pt; color: #333; margin-top: 0.6mm; }
.v-meta b { color: #111; }

.v-qr { flex: 0 0 auto; display: flex; align-items: flex-start; justify-content: flex-end; }
.qrcode {
  width: 14mm; height: 14mm; padding: 0.4mm;
  border: 0.3mm solid #444; border-radius: 0.6mm; background: #fff;
}
.qrcode img, .qrcode canvas, .qrcode table { width: 100% !important; height: 100% !important; display: block; }

.v-foot {
  flex: 0 0 auto; display: flex; align-items: center; justify-content: space-between;
  padding: 0.6mm 1.3mm; color: #fff;
}
.v-foot .warn { font-size: 4.4pt; font-weight: bold; }
.v-foot .num { font-size: 4.4pt; font-weight: bold; }

/* Pratinjau di layar (tidak memengaruhi hasil cetak) */
@media screen {
  body { background: #e9e9e9; padding: 12px; }
  .vsheet { width: 195mm; margin: 0 auto; background: #fff; }
}
</style>
<div class="vsheet">
{/if}

{assign var="price" value=$vs['total']}
{if $price eq '2000'}
{assign var="color" value='#ff3c00'}
{elseif $price eq '3000'}
{assign var="color" value='#0080ff'}
{elseif $price eq '5000'}
{assign var="color" value='#ff3c00'}
{elseif $price eq '6000'}
{assign var="color" value='#FF4500'}
{elseif $price eq '20000'}
{assign var="color" value='#C23777'}
{elseif $price eq '50000'}
{assign var="color" value='#BA68C8'}
{else}
{assign var="color" value='#3058d1'}
{/if}

<div class="voucher">
  <div class="v-card" style="border-color:{$color}">

    <div class="v-strip" style="background:{$color}"></div>

    <div class="v-body">

      <div class="v-head">
        <span class="v-logo"><img src="{$_theme}images/{$_c['show-logo']}" alt=""></span>
        <span class="v-price" style="color:{$color}"><span class="cur">{$_c['currency_code']}</span>{number_format($vs['total'],0,$_c['dec_point'],$_c['thousands_sep'])}</span>
      </div>

      <div class="v-mid">
        <div class="v-info">
          {if $vs['secret'] eq $vs['code']}
          <div class="v-label">Kode Voucher</div>
          <div class="v-code" style="color:{$color}">{$vs['code']}</div>
          {else}
          <div class="v-label">Member</div>
          <div class="v-code-sm" style="color:{$color}">{$vs['code']}<br>{$vs['secret']}</div>
          {/if}
          <div class="v-meta">
            Durasi: <b>{$vs['timelimit']}</b><br>
            Masa aktif: <b>{$vs['validperiod']}</b><br>
            Agen: <b>{$vs['owner_name']}</b>
          </div>
        </div>

        <div class="v-qr">
          <div class="qrcode" id="{$vs['code']}" style="border-color:{$color}"></div>
        </div>
      </div>

    </div>

    <div class="v-foot" style="background:{$color}">
      <span class="warn">JANGAN DIBUANG SELAMA MASIH AKTIF</span>
      <span class="num">[{$vs['number']}]</span>
    </div>

  </div>
</div>

{if $vs@last}
</div>
{/if}

<!-- ############################# -->
<!-- END CUSTOM HTML -->
<!-- don't include closing body tag -->
{/foreach} <!-- DON'T REMOVE THIS LINE -->
{include file="rad-template-footer.tpl"} <!-- DON'T REMOVE THIS LINE -->
