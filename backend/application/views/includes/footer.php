</div>
 <!-- Footer -->
            <div class="hk-footer-wrap container-fluid px-xxl-65 px-xl-20">
                <footer class="footer">
                    <div class="row">
                        
                       
                    </div>
                </footer>
            </div>
            <!-- /Footer -->
        </div>
    </div>
    <!-- /HK Wrapper -->
<!-- plugins:js -->
<script src="<?= base_url(); ?>asset/node_modules/popper.js/dist/umd/popper.min.js"></script>
<script src="<?= base_url(); ?>asset/node_modules/perfect-scrollbar/dist/js/perfect-scrollbar.jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/16.0.8/js/intlTelInput-jquery.min.js"></script>
<!-- endinject -->

<!-- Plugin js for this page-->
<script src="<?= base_url(); ?>asset/node_modules/jquery-bar-rating/dist/jquery.barrating.min.js"></script>
<script src="<?= base_url(); ?>asset/node_modules/raphael/raphael.min.js"></script>
<script src="<?= base_url(); ?>asset/node_modules/dropify/dist/js/dropify.min.js"></script>
<script src="<?= base_url(); ?>asset/node_modules/icheck/icheck.min.js"></script>
<script src="<?= base_url(); ?>asset/node_modules/typeahead.js/dist/typeahead.bundle.min.js"></script>
<script src="<?= base_url(); ?>asset/node_modules/select2/dist/js/select2.min.js"></script>
<script src="<?= base_url(); ?>asset/node_modules/dragula/dist/dragula.min.js"></script>
<script src="<?= base_url(); ?>asset/node_modules/summernote/dist/summernote-bs4.min.js"></script>
<script src="<?= base_url(); ?>asset/vendors/tinymce/tinymce.min.js" referrerpolicy="origin"></script>
<script>
function previewFile() {
  var preview = document.querySelector('foto_sim');
  var file    = document.querySelector('input[type=file]').files[0];
  var reader  = new FileReader();

  reader.onloadend = function () {
    preview.src = reader.result;
  }

  if (file) {
    reader.readAsDataURL(file);
  } else {
    preview.src = "";
  }
}
</script>
<script>
tinymce.init({
selector: "textarea",
theme: "modern",
plugins: [
"advlist autolink link image lists charmap print preview hr anchor pagebreak spellchecker",
"searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
"save table contextmenu directionality emoticons template paste textcolor importcss colorpicker textpattern"
],
external_plugins: {
//"moxiemanager": "/moxiemanager-php/plugin.js"
},
add_unload_trigger: false,
 
toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | print preview media fullpage | forecolor backcolor emoticons table",
 
image_advtab: true,
 
style_formats: [
{title: 'Bold text', format: 'h1'},
{title: 'Red text', inline: 'span', styles: {color: '#ff0000'}},
{title: 'Red header', block: 'h1', styles: {color: '#ff0000'}},
{title: 'Example 1', inline: 'span', classes: 'example1'},
{title: 'Example 2', inline: 'span', classes: 'example2'},
{title: 'Table styles'},
{title: 'Table row 1', selector: 'tr', classes: 'tablerow1'}
],
 
template_replace_values : {
username : "Jack Black"
},
 
template_preview_replace_values : {
username : "Preview user name"
},
 
link_class_list: [
{title: 'Example 1', value: 'example1'},
{title: 'Example 2', value: 'example2'}
],
 
image_class_list: [
{title: 'Example 1', value: 'example1'},
{title: 'Example 2', value: 'example2'}
],
 
templates: [
{title: 'Some title 1', description: 'Some desc 1', content: '<strong class="red">My content: {$username}</strong>'},
{title: 'Some title 2', description: 'Some desc 2', url: 'development.html'}
],
 
setup: function(ed) {
/*ed.on(
'Init PreInit PostRender PreProcess PostProcess BeforeExecCommand ExecCommand Activate Deactivate ' +
'NodeChange SetAttrib Load Save BeforeSetContent SetContent BeforeGetContent GetContent Remove Show Hide' +
'Change Undo Redo AddUndo BeforeAddUndo', function(e) {
console.log(e.type, e);
});*/
},
 
spellchecker_callback: function(method, data, success) {
if (method == "spellcheck") {
var words = data.match(this.getWordCharPattern());
var suggestions = {};
 
for (var i = 0; i < words.length; i++) {
suggestions[words[i]] = ["First", "second"];
}
 
success({words: suggestions, dictionary: true});
}
 
if (method == "addToDictionary") {
success();
}
}
});
</script>
<script>tinymce.init({forced_root_block : "",selector:'textarea'});</script>
  </script>
<script src="<?= base_url(); ?>asset/node_modules/quill/dist/quill.min.js"></script>
<script src="<?= base_url(); ?>asset/node_modules/simplemde/dist/simplemde.min.js"></script>
<!-- End plugin js for this page-->
    <!-- jQuery -->
    <script src="<?= base_url(); ?>vendors/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="<?= base_url(); ?>vendors/popper.js/dist/umd/popper.min.js"></script>
    <script src="<?= base_url(); ?>vendors/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Slimscroll JavaScript -->
    <script src="<?= base_url(); ?>dist/js/jquery.slimscroll.js"></script>

    <!-- Fancy Dropdown JS -->
    <script src="<?= base_url(); ?>dist/js/dropdown-bootstrap-extended.js"></script>
	
	<!-- MWTABLE JS -->
    <script src="<?= base_url(); ?>dist/js/mwtable.js"></script>

    <!-- FeatherIcons JavaScript -->
    <script src="<?= base_url(); ?>dist/js/feather.min.js"></script>

    <!-- Toggles JavaScript -->
    <script src="<?= base_url(); ?>vendors/jquery-toggles/toggles.min.js"></script>
    <script src="<?= base_url(); ?>dist/js/toggle-data.js"></script>
	
	<!-- Morris Charts JavaScript -->
    <script src="<?= base_url(); ?>vendors/raphael/raphael.min.js"></script>
    <script src="<?= base_url(); ?>vendors/morris.js/morris.min.js"></script>
	
	<!-- Counter Animation JavaScript -->
	<script src="<?= base_url(); ?>vendors/waypoints/lib/jquery.waypoints.min.js"></script>
	<script src="<?= base_url(); ?>vendors/jquery.counterup/jquery.counterup.min.js"></script>
	
	<!-- EChartJS JavaScript -->
    <script src="<?= base_url(); ?>vendors/echarts/dist/echarts-en.min.js"></script>
    
	<!-- Sparkline JavaScript -->
    <script src="<?= base_url(); ?>vendors/jquery.sparkline/dist/jquery.sparkline.min.js"></script>
	
	<!-- Vector Maps JavaScript -->
    <script src="<?= base_url(); ?>vendors/vectormap/jquery-jvectormap-2.0.3.min.js"></script>
    <script src="<?= base_url(); ?>vendors/vectormap/jquery-jvectormap-world-mill-en.js"></script>
    <script src="<?= base_url(); ?>dist/js/vectormap-data.js"></script>

	<!-- Owl JavaScript -->
    <script src="<?= base_url(); ?>vendors/owl.carousel/dist/owl.carousel.min.js"></script>
	
	<!-- Toastr JS -->
    <script src="<?= base_url(); ?>vendors/jquery-toast-plugin/dist/jquery.toast.min.js"></script>
    
    <!-- Init JavaScript -->
    <script src="<?= base_url(); ?>dist/js/init.js"></script>
	<!--<script src="<?= base_url(); ?>dist/js/dashboard-data.js"></script>-->
	 <!-- Data Table JavaScript -->
    <script src="<?= base_url(); ?>vendors/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="<?= base_url(); ?>vendors/datatables.net-bs4/js/dataTables.bootstrap4.min.js"></script>
    <script src="<?= base_url(); ?>vendors/datatables.net-dt/js/dataTables.dataTables.min.js"></script>
    <script src="<?= base_url(); ?>vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
    <script src="<?= base_url(); ?>vendors/datatables.net-buttons-bs4/js/buttons.bootstrap4.min.js"></script>
    <script src="<?= base_url(); ?>vendors/datatables.net-buttons/js/buttons.flash.min.js"></script>
    <script src="<?= base_url(); ?>vendors/jszip/dist/jszip.min.js"></script>
    <script src="<?= base_url(); ?>vendors/pdfmake/build/pdfmake.min.js"></script>
    <script src="<?= base_url(); ?>vendors/pdfmake/build/vfs_fonts.js"></script>
    <script src="<?= base_url(); ?>vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
    <script src="<?= base_url(); ?>vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
    <script src="<?= base_url(); ?>vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
    <script src="<?= base_url(); ?>dist/js/dataTables-data.js"></script>
	 <script src="https://cdn.datatables.net/fixedcolumns/3.3.2/js/dataTables.fixedColumns.min.js"></script>
	  <script src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.js"></script>

	  <script type="text/javascript">
  $(document).ready( function () {
  var table = $('#mwtable').DataTable({
    responsive: {
            details: {
                type: 'column'
            }
        },
        columnDefs: [ {
            className: 'control',
            orderable: false,
            targets:   0
        } ],
        order: [ 1, 'asc' ]
  });
} );
</script>
<script type="text/javascript">
  $(document).ready( function () {
  var table = $('#mwtable1').DataTable({
    responsive: {
            details: {
                type: 'column'
            }
        },
        columnDefs: [ {
            className: 'control',
            orderable: false,
            targets:   0
        } ],
        order: [ 1, 'asc' ]
  });
} );
</script>
<script type="text/javascript">
  $(document).ready( function () {
  var table = $('#mwtable2').DataTable({
    responsive: {
            details: {
                type: 'column'
            }
        },
        columnDefs: [ {
            className: 'control',
            orderable: false,
            targets:   0
        } ],
        order: [ 1, 'asc' ]
  });
} );
</script>
<script type="text/javascript">
  $(document).ready( function () {
  var table = $('#mwtable3').DataTable({
    responsive: {
            details: {
                type: 'column'
            }
        },
        columnDefs: [ {
            className: 'control',
            orderable: false,
            targets:   0
        } ],
        order: [ 1, 'asc' ]
  });
} );
</script>
<script type="text/javascript">
$(document).ready(function() {
$('#tabhistori').DataTable({
		responsive: true,
		autoWidth: false,
        scrollCollapse: true,
		fixedColumns: true,
		dom: 'lrtip',
		pageLength: 10,
		
        fixedColumns: true,
		language: { search: "",
		searchPlaceholder: "Search",
		sLengthMenu: "_MENU_items",
		buttons: [
			'copy', 'csv', 'excel', 'pdf', 'print'
		]
		}
	});
</script>
<script type="text/javascript">
$(document).ready(function() {
$('#tabhistori2').DataTable({
		responsive: true,
		autoWidth: false,
        scrollCollapse: true,
		fixedColumns: true,
		dom: 'lrtip',
		pageLength: 10,
		
        fixedColumns: true,
		language: { search: "",
		searchPlaceholder: "Search",
		sLengthMenu: "_MENU_items",
		buttons: [
			'copy', 'csv', 'excel', 'pdf', 'print'
		]
		}
	});
</script>
<script type="text/javascript">
$('#tabwallet').DataTable({
		responsive: true,
		autoWidth: false,
        scrollCollapse: true,
		fixedColumns: true,
		dom: 'lrtip',
		pageLength: 10,
		
        fixedColumns: true,
		language: { search: "",
		searchPlaceholder: "Search",
		sLengthMenu: "_MENU_items",
		buttons: [
			'copy', 'csv', 'excel', 'pdf', 'print'
		]
		}
	});

</script>

<script type="text/javascript">
$('#tabwallet2').DataTable({
		responsive: true,
		autoWidth: false,
        scrollCollapse: true,
		fixedColumns: true,
		dom: 'lrtip',
		pageLength: 10,
        fixedColumns: true,
		language: { search: "",
		searchPlaceholder: "Search",
		sLengthMenu: "_MENU_items",
		buttons: [
			'copy', 'csv', 'excel', 'pdf', 'print'
		]
		}
	});

</script>
<script type="text/javascript">
$('#tabwallet3').DataTable({
		responsive: true,
		autoWidth: false,
        scrollCollapse: true,
		fixedColumns: true,
		dom: 'lrtip',
		pageLength: 10,
		
        fixedColumns: true,
		language: { search: "",
		searchPlaceholder: "Search",
		sLengthMenu: "_MENU_items",
		buttons: [
			'copy', 'csv', 'excel', 'pdf', 'print'
		]
		}
	});

</script>
<script type="text/javascript">
$('#tabwallet4').DataTable({
		responsive: true,
		autoWidth: false,
        scrollCollapse: true,
		fixedColumns: true,
		dom: 'lrtip',
		pageLength: 10,
		
        fixedColumns: true,
		language: { search: "",
		searchPlaceholder: "Search",
		sLengthMenu: "_MENU_items",
		buttons: [
			'copy', 'csv', 'excel', 'pdf', 'print'
		]
		}
	});

</script>
</body>

</html>