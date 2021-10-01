<!doctype html>
<html lang="en">
<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<!-- Bootstrap CSS -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
	<title>Prova API</title>
	
	<style>

		
		.container {
			background-color: #E7E9EB;    
			margin: 10px;
			padding: 10px;
		}
		.div_arq {
			background-color: #E7E9EB;    
			padding: 10px;
			border-width: 2px;
			border-style: solid;
			border-color: #ab7f1b; 
			margin-bottom: 10px;
		}
		
		.div_resuts {
			background-color: #E7E9EB;     
			padding: 10px;
		}
	</style>
</head>
<body>
	<div class="container">		
		<div class="row text-center ">
			<p >Desafio Fluid - Analista de integrações</p>
		</div>
		<div class="row">
			<div class=" col-12 col-md-6">
				<div class="row div_arq">
					<form id="f_form" class="row g-3"  method="post" action="" enctype='multipart/form-data'>
						<div class="col-12 text-center">
							<p>Sistema DE - envio arquivo (POST)</p>
						</div>
						<div class="col-12 mb-3">
							<label for="f_file" class="form-label">Selecione o arquivo para envio</label>
							<input id="f_file" name="f_file" class="form-control" type="file" >
						</div>
							<button id='f_btn' type="submit" class="btn btn-primary mb-3"  >Enviar</button>
						
					</form>				
				</div>
				<div class="row div_arq">
					<div class="col-12 text-center">
						<p>Sistema PARA - recebimento (GET)</p>
					</div>				
					<button id='f_get_json' type="submit" class="btn btn-success mb-3"  >Get Estruta</button>
				</div>
			</div>
			<div class="div_resuts col-12 col-md-6">
				<div class="form-group col-12 col-sm-12 col-md-12   col-lg-12   ">
					<label for="v_capa_obs">Resutado API:</label>
					<textarea id="resul_area" class="form-control" rows="13" ></textarea>
				</div>
			</div>
		</div>
	</div>

<div class="sidebar_overlay"></div>
		<!-- Modal GEnerico -->
		<div class="modal fade" id="modal_msg" aria-labelledby="modal_msg_title" data-backdrop="true">
		  <div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<label class="modal-title" id="modal_msg_title">Modal title</label>
						 <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class="modal-body" id="modal_msg_body">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" id="modal_msg_save2" >Save changes</button>
						<button type="button" class="btn btn-secondary" data-dismiss="modal" id="modal_msg_close" >Close</button>
						<button type="button" class="btn btn-primary" id="modal_msg_save" >Save changes</button>
					</div>
				</div>
		  </div>
		</div>




    <!-- Scripts -->
	
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	
	
	
<script>	
	
/* 	
####################################################
#########   		Form 					########
#################################################### */
	
	var $f_btn = $('#f_btn');
	var $f_get_json = $('#f_get_json');
	var $f_file = $('#f_file');
	var $f_form = $('#f_form');
	
	var $r_area = $('#resul_area');
	
	
	$f_get_json.click(function(){
		
		//obtem info
			var j_data = '';//JSON.stringify(p_marker);	
			var url_config = 'src/api.php';
		
		//executa
			get_ajax(j_data,url_config,function(data){				
					//sucesso	
					$r_area.val(JSON.stringify(data, null, '\t'));	
				},function(data){				
					//erro		
					
					$r_area.val(JSON.stringify(data, null, '\t'));	
				},function(data){	
					//pre
					$r_area.val('Processando GET');	
				});
	});
	
	
	


	$f_form.on('submit',function(e) {
		  e.preventDefault();
			  
		if ( jQuery.isBlank( $f_file.val() ) ){exibe_modal_msg("Erro","Não foi informado um arquivo",'','',"Fechar");return;}
		
		//obtem info
			var formData = new FormData(this);	
			var url_config = 'src/api.php';
		
		//executa
			post_ajax_file(formData,url_config,function(data){				
					//sucesso	
					$r_area.val(JSON.stringify(data, null, '\t'));	
				},function(data){				
					//erro		
					
					$r_area.val(JSON.stringify(data, null, '\t'));	
				},function(data){	
					//pre
					$r_area.val('Processando POST');	
				});
		/*
		setTimeout(function () {
			$r_area.val('limpo');
		}, 1000);
		*/
		
	});
	

/* 	####################################################
	#########   		AJAX FILE 				########
	#################################################### */
	/*
	// funcao padrao de AJAX e AJAX  com o envio de ARQUIVO <input type=file>
	// se error: exibe msg
	// se sucesso: retorna info para tratamento.	
	// OBS:
		os campos precisa ser dentro de um FORM com submit
			<form id='form_exemplo' method="post" action="" enctype='multipart/form-data'>
	*/
	
		
	function get_ajax(p_data,p_url,p_fnc_success,p_fnc_error,p_fnc_pre){		
		$.ajax({
			type: "get",
			url: p_url,
			data: p_data,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			beforeSend: function(data) {
				if (jQuery.isBlank(  p_fnc_pre ) ){ p_fnc_pre(data);}
			},
			success: function(p_data){
				console.log(JSON.stringify(p_data));		
				if ( ! jQuery.isBlank(  p_fnc_success ) ){p_fnc_success(p_data);}
			},
			error: function(data) {
				console.log('Error: ' +  JSON.stringify(data.responseText)); 				
				if (! jQuery.isBlank(  p_fnc_error ) ){ p_fnc_error(data);}				
			},
			failure: function(errMsg) {
				console.log('Failure: ' +  errMsg ); 				
				if (!jQuery.isBlank(  p_fnc_error ) ){p_fnc_error(errMsg);}
			}
		});		
	}
	
	
	function post_ajax_file(p_form,p_url,p_fnc_success,p_fnc_error,p_fnc_pre){		
		$.ajax({
			type: "POST",
			url: p_url,
			data: p_form,
			dataType: "json",
			contentType: false,
			cache: false,
			processData:false,
			beforeSend: function(data) {
				if (jQuery.isBlank(  p_fnc_pre ) ){ p_fnc_pre(data);}
			},
			success: function(p_data){
				console.log(JSON.stringify(p_data));		
				if ( ! jQuery.isBlank(  p_fnc_success ) ){p_fnc_success(p_data);}
			},
			error: function(data) {
				console.log('Error: ' +  JSON.stringify(data.responseText)); 				
				if (! jQuery.isBlank(  p_fnc_error ) ){ p_fnc_error(data);}				
			},
			failure: function(errMsg) {
				console.log('Failure: ' +  errMsg ); 				
				if (!jQuery.isBlank(  p_fnc_error ) ){p_fnc_error(errMsg);}
			}
		});		
	}
	/* 	
	####################################################
	#########   		MODAL 					########
	#################################################### */
	// modal generico utilizado para diversas funcionalidades.


	function exibe_modal_msg(p_title,p_body,btn_save_name,btn_save_action,btn_close_name,btn_close_action,btn_save2_name,btn_save2_action){
			$('#modal_msg_title').text(p_title);
			$('#modal_msg_body').html((p_body));
			
			//save2
			if (typeof btn_save2_name === 'undefined' || btn_save2_name == ''   || btn_save2_name == null ) {
				$('#modal_msg_save2').hide() ;	
			}else{
				$('#modal_msg_save2').text(btn_save2_name);
				$('#modal_msg_save2').unbind();
				$("#modal_msg_save2").click(function() { $('#modal_msg').modal('hide'); });
				$("#modal_msg_save2").click(btn_save2_action);
				$('#modal_msg_save2').show() ;				
			}
			
			
			
			//save1			
			if (typeof btn_save_action === 'undefined' || btn_save_action == ''   || btn_save_action == null ) {
				btn_save_action = function() { $('#modal_msg').modal('hide'); } ;
			}
			
			if ( btn_save_name != ""){
				$('#modal_msg_save').text(btn_save_name);
				$('#modal_msg_save').unbind();
				$("#modal_msg_save").click(function() { $('#modal_msg').modal('hide'); });
				$("#modal_msg_save").click(btn_save_action);
				$('#modal_msg_save').show() ;
			}else{
				$('#modal_msg_save').hide() ;				
			}
				
			if ( btn_close_name == ""){
				$('#modal_msg_close').hide() ;
			}else{
				
				$('#modal_msg_close').text(btn_close_name);
				$('#modal_msg_close').unbind();
				$("#modal_msg_close").click(function() { $('#modal_msg').modal('hide'); });
				$("#modal_msg_close").click(btn_close_action);
				$('#modal_msg_close').show() ;
				
			}
			//corrige o botao para o portugues
			if (jQuery.isBlank(btn_close_name)) {
				$('#modal_msg_close').text('Fechar');
			}
			
				//setTimeout(function(){
			$('#modal_msg').modal('show'); 
				//},5000);
		}
		
		$('#modal_msg').on('hidden.bs.modal', function (e) {
		  // remove todos os backdrop .
		  $('.modal-backdrop.in').hide();
		});
	
/* 	####################################################
	#########   		JQUERY new FNC			########
	#################################################### */
	// cria uma func no jquery validado se info é em branco.. independete do TYPEOF da variavel. INT, text, obj, array. etc.
		(function($){ 
		  $.isBlank = function(obj){
			return( (obj === undefined) || $.trim(obj) === "");
		  };
		})(jQuery);
	
</script>
 </body>
</html>