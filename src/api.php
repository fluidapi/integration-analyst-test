<?php
/*!
 * API versao 1.0
 * Desafio Fluid - Analista de integrações
 * By Marco Desiato, setembro 2021
 *
 */
 
 
 /*
	Explicação:
		foi dividido em 3 partes:
			Modulo IN -> recebe o arquivo e converte para uma forma padrao de linhas(Modulo_in_obj - IN_CAMPOS); 
			Modulo Middle -> obtem a forma padrao de linhas e agrupa conforme a regra
			Modulo out -> saida do arquivo, 
	
	Modulo IN:
		- criado uma interface padrao para cada tipo de arquivo (extensao- csv,xml,json etc )
		- Modulo_in_obj ->  estrutura de conversao de todos os tipos de entrada
		- Modulo_in_get_obj_by_Type -> fnc que seleciona qual class de entrada que ira tratar o arquivo
		
		
	Modulo Middle:
		- trabalha as informações recebida da estrutra padrao
			- pode agrupar por data ou por qq outro ( ex:estado)
			- pode ordernar ( nao desevolvido)
		- retorna a estrutra prota 
	
	
	Modulo OUT:
		- local onde a estura recebida pelo sistema IN irá ser armazenada até o sistema OUT ler
		- pode ser gravado de diversas maneiras, nesse caso.. usei a propria session.( apenas para teste)
		
 */

		
//###################################
//			PARAMETROS GLOBAIS
//###################################
	//modulo In
		define('IN_CAMPOS',
			'[
				{"name":"date", "type":"date", "format":"Y-m-d", "allownull":false},
				{"name":"cases","type":"number",  "opp": ">=", "val": 0, "allownull":false },
				{"name":"deaths","type":"number", "opp": ">=", "val": 0 , "allownull":false},
				{"name":"uf","type":"string", "length":2, "allownull":false},
				{"name":"time","type":"date","format":"H:i" , "allownull":true}
			]'
		); //validações de campos permitidos 
		
	//modulo Middle
		define('MI_GROUPFORMARTDATE',"Y-m"); //formato do grupo  de linhas.. nesse caso ano-mes;
	
	//modulo out
		define('OUT_TIPO_SAVE','SESSION');  // qual o tipo do local que irá salvar a estrutura, memoria, SESSION, mysql.. etc
	//Resposta
		define('POST_RETORNAARRAY',0); // 0 - Retorna apenas msg que o arquivo foi recebido; 1- retorna o array manipulado
		define('POST_msg',"Arquivo enviado, validado e processado com sucesso!"); // Se POST_RETORNAARRAY = 0, entao retorna essa msg
		
	
//###################################
//			Modulo IN 
//###################################

	interface Modulo_in_interface {
		//construtor do modulo de entrada. Obtem informação e guarda internamente
			public function __construct(Arquivo $arquivo);
		// funcao que valida informação obtida no construtor
			public function valida();
		// obtem arquivo validado
			public function get_obj_validado();
		//obtem a extensão do arquivo que será tratado. (STATIC), sempre em maiuscula ( PDV,XML, JSON)
			public static function getExt(); 
		
	}
	/*
		class in_xml implements in_inter {
			
			private $obj;
			
			public function valida() {
				
			}
		 
			public function open() {
				
			}
		}
	*/


	class Modulo_in_csv implements Modulo_in_interface {
		
		private $arquivo;
		private $obj;
		
		public function __construct( Arquivo $p_arquivo){
			$this->arquivo = $p_arquivo;
		}
		public function valida() {
			//valida cada linha se tem todas infos
			
			$obj_tmp = new Modulo_in_obj();
						
			//manipula arquvi CSV			
			if ($this->arquivo->size > 0) {				
				if( ($handle = fopen($this->arquivo->fileName, "r")) !== FALSE) {
					$csv = Array();
					$rowcount = 0;					
					$max_line_length =  10000;
					
					// get header
						$header = fgetcsv($handle, $max_line_length);					  
						$header_colcount = count($header);
					
					//valida header
						if ( ! $obj_tmp->valida_header($header) ){
							Retorna_error(2,"Formato do arquivo inválido!");	
						}						
					
					//Cada linha, tenta add 
						while (($row = fgetcsv($handle, $max_line_length)) !== FALSE) {						
							$obj_tmp->add_row($row,$rowcount);						
							$rowcount++;
						}					
				}				
			}
			//sucesso
				$this->obj = $obj_tmp;
		}
		public static  function getExt() {
			return "CSV";
		}
		public function get_obj_validado() {
			return $this->obj;
		}
		
	}
	class Modulo_in_obj {		
		/*
			Funcao: classe de conversao de todas os tipos de entrada
			Acao: independenete do formado do arquivo, será convertido para esse tipo de objeto.
			Obs:
		*/
		
		private $campos_in ; //campos 
		private $count_campos;
		private $arry_row;	// array com cada linha
		
		
		public function __construct(){
			//obtem do parametros globais os campos relacionados
			$this->campos_in 	= json_decode(IN_CAMPOS);		
			$this->arry_row 	= array();		
			$this->count_campos = count($this->campos_in);
		}
		public function valida_header($p_arr_header){
			/*
				Fnc: que valida se as colunas são iguais, retornando true ou false;
				Param: array com os nomes das colunas
			*/
			
			//valida a quantidade de colunas são iguais
				if ( count($p_arr_header) !== $this->count_campos ) return false;
			
			//compara campo por campo se o nome são os mesmos
				foreach ($p_arr_header as $i => $item){
					if ( strcasecmp( $item, $this->campos_in[$i]->name) !== 0 )return false;
				}
				
			return true;
		}
		public function add_row($p_arr_row,$p_linha){			
			/*
				Fnc: Adiciona uma linha com as informações passada por parametros, validando cada campo
				Param: array com cada informação
			*/
			//verifica a qnt de colunas são certas
				if ( count($p_arr_row) !== $this->count_campos ){
					Retorna_error(1, "Inválido numero de colunas na linha ( " . ($p_linha +2) . "). Esperava= ".$this->count_campos);
					return false;
				}		
			//verifica cada campo
				$a_tmp = new stdClass();	
				
				foreach ($p_arr_row as $i => $item){
					$resp =  Modulo_in_valida_rules ( $this->campos_in[$i], $item );
					
					if (!$resp->cont){	Retorna_error(1,$resp->msg." (Linha:" . ($p_linha +2) . ")");}
					
					//guarda info em cada path 
						$campo = $this->campos_in[$i]->name;						
						$a_tmp->$campo = $resp->info;
					
				}				
				
			//se todos os campos foram inseridos com sucesso, add o a_tmp no array 
				array_push($this->arry_row,$a_tmp);
		}
		public function get_rows(){
			return $this->arry_row;
		}
	}
	function Modulo_in_get_obj_by_Type(Arquivo $p_arq){	
		/*
			Funcao: fnc que seleciona qual class de entrada que ira tratar o arquivo
			Acao: Pelo formado do arquivo, encontra a classe correspondete
			Obs:
		*/
		
		$obj_in= null;
		
		switch ( strtoupper( $p_arq->ext) ) {
			case Modulo_in_csv::getExt() :
				$obj_in = new Modulo_in_csv($p_arq);
				break;
			default:
				Retorna_error(2,"Tipo de arquivo não implementado. (".$p_arq->ext.")");	
		}
		
			
		return $obj_in;
	}	
	function Modulo_in_valida_rules($p_rules,$p_info){
		/*
			Funcao: valida regra para cada informaçao 
			Acao:
				"type":
					"date" -> "format":"y-m-d", "allownull":false
					"number" ->  "opp": ">=", "val": 0, "allownull":false
					"string" ->  "length": "",  "allownull":false
				
			Obs: poderia usar o regexp	ou jsonlogic	
				op = '=' | '<=' | '>=' | '>' | '<' | 'LIKE' | 'IN' | 'NOT IN';
		
		*/
		
		$resp = new stdClass();
		$resp->cont = true;
		$resp->msg = '';
		$resp->info = '';
		
		//erro na regra
			if (!isset($p_rules->type) ){$resp->cont = false;$resp->msg = "Tipo da regra não informado"; return $resp;}
		
		//analisa campo vazio . se permitir e for vazio, retorna info. Se n permitir e for vazio, retorna erro.
			if (isset($p_rules->allownull) ){
				if (! $p_rules->allownull and ( is_null($p_info) or $p_info == "" ) ){ $resp->cont = false;$resp->msg = "Campo vazio não permitido!"; return $resp;		}
				if ( $p_rules->allownull and ( is_null($p_info) or $p_info == "" ) ){  return $resp;	}
			}
		
		//analisa por tipo
			switch ($p_rules->type) {
				case "date":
					if (isset($p_rules->format) ){
						$d = DateTime::createFromFormat($p_rules->format, $p_info);
						if(  $d && $d->format($p_rules->format) === $p_info ){
							$resp->info = $p_info;
							return $resp;
						}else{
							$resp->cont = false;$resp->msg = "Formato de data inválido.(".$p_info.")";	return $resp;
						}
					}else{
						if (date('Y-m-d', strtotime($p_info)) === $p_info){							
							$resp->info = $p_info;
							return $resp;
						}else{
							$resp->cont = false;$resp->msg = "Formato de data inválido.(".$p_info.")";	return $resp;
						}	
					}					
					break;
				case "number":
					if ( is_number($p_info) ){
						$resp->info = intval( $p_info);
					}else{
						$resp->cont = false;$resp->msg = "Informação passada não é numero.(".$p_info.")";	return $resp;
					}
				
					if (isset($p_rules->opp) ){
						if ($p_rules->opp ==  ">=" ){
							if ( $resp->info < 0 ){
								$resp->cont = false;$resp->msg = "Informação passada não é maior ou igual a zero .(".$p_info.")" ;	return $resp;								
							}
						}
					}					
					break;
				case "string":
				
					if (isset($p_rules->length) ){
						if ( strlen($p_info) != $p_rules->length ){
							$resp->cont = false;$resp->msg = "Informação passada não possui o tamanho esperado! ( Tamanho:".$p_rules->length .")" ."(".$p_info.")" ;	return $resp;
						}
					}
										
					$resp->info = $p_info;
					return $resp;
					
					break;
				default:
					Retorna_error(1,"Função n implementada");	
			}
			
			

		return $resp;
	}


//###################################
//			Modulo Middle
//###################################


	class Modulo_middle_obj {		
		/*
			Funcao: recebe o objeto da classe in e trata ( agrupa)
			Acao: 
			Obs:
				pode-se implementar ordernação, ou outro tipo de grupos(ex:por estado);
		*/
		
		private $obj_in ; 
		
		private $array_out ; 
		
		public function __construct(Modulo_in_obj $p_in ){
			$this->obj_in 	=  $p_in;	
		}
		public function agruparByDate(){
			/*
				Funcao: recebe o objeto da classe in e agrupa por data
				Acao: usando o conceito de array reduce
				Obs:
			*/
			
			$this->array_out = array_reduce($this->obj_in->get_rows(), function($accumulator, $item) {
				
				$id = $this->getIdFormatadoDate($item->date);
				if (!isset($accumulator[$id] )) {
					$aux = new stdClass();
					$aux->month = $id;
					$aux->cases = $item->cases;
					$aux->deaths = $item->deaths;					 	
					$accumulator[$id] =$aux;				
				}else{
					$aux = $accumulator[$id] ;
					$aux->cases 	= $aux->cases  +$item->cases;
					$aux->deaths	= $aux->deaths + $item->deaths;					 	
					$accumulator[$id] =$aux;	
				}	
				return $accumulator;

			}, []);
			
		}		
		private function getIdFormatadoDate($p_info){			
			/*
				Funcao: Formata o id acumulador de cada grupo, nesse caso Por data.
				Acao: Obtem o formado nas definições globais e aplica o padrao
				Obs:
			*/			
			$d = DateTime::createFromFormat("Y-m-d", $p_info);
			return $d->format(MI_GROUPFORMARTDATE);
		}
		public function getArrayResp(){	
			/*
				Funcao: Obtem o array agrupado e formata ele no padrao solicitado
				Acao: 
				Obs:
					só remove as chaves de cada item
			*/	
			
			$new_array =  Array();
			
			if (	is_array($this->array_out) ) {
				foreach ($this->array_out as $i => $item){
					array_push($new_array,$item);	
				}			
			}
			
			return $new_array;
		}
	}

//###################################
//			Modulo Out 
//###################################

	interface Modulo_out_interface {
		//construtor do modulo de out. Obtem informação e guarda internamente
			public function __construct(Array $p_arr);
		// funcao que obtem
			public function get();
		// funcao que salva
			public function save();		
		//obtem tipo salvamento (STATIC), sempre em maiuscula ( session, cokie, banco, json, )
			public static function getTipo(); 
	}	

	class Modulo_out_session implements Modulo_out_interface {
		
		private $arr;
		
		public function __construct( Array $p_arr){
			$this->arr = $p_arr;
		}
		// funcao que obtem
			public function get(){
				//inicia sessao se nao estiver ativa
				if(!isset($_SESSION)){ session_start();  } 
				
				if (isset($_SESSION['arr'])){
					return  json_decode( $_SESSION['arr'] ) ;
				}				
				return [];
			}
		// funcao que salva
			public function save(){				
				//inicia sessao se nao estiver ativa
				if(!isset($_SESSION)){ session_start();  } 
				$_SESSION['arr'] = json_encode( $this->arr);
				 
			}	
		
		public static  function getTipo() {
			return "SESSION";
		}
	}



	function Modulo_out_get_obj_by_Type(Array $p_arr){	
		/*
			Funcao: fnc que seleciona qual class de entrada que ira tratar o arquivo
			Acao: Pelo formado do arquivo, encontra a classe correspondete
			Obs:
		*/
		
		$obj_out= null;
		
		switch ( strtoupper( OUT_TIPO_SAVE ) ) {
			case Modulo_out_session::getTipo() :
				$obj_out = new Modulo_out_session($p_arr);
				break;
			default:
				Retorna_error(2,"Tipo gravar arquivo não implementado. (".OUT_TIPO_SAVE.")");	
		}
		
		return $obj_out;
	}
	


//###################################
//			Globais 
//###################################
	
	class Arquivo {
		public $valido = false;			
		public $nome;
		public $ext;
		public $info;
		public $size;
		public $fileName;
		
	}	
	function Retorna_error($p_tipo,$p_info){
		/*
			Funcao: Retorno de erro.
			Acao: pode escolher a forma de exibir o erro.
				exit, http_response_code , throw new Error etc.
				http_response_code(404);
				throw new Error($info);
			Obs:
		*/
		
		exit($p_info ."( erroCode:". htmlentities($p_tipo) . ")");
		
	}	
	function Retorna_info($p_info){
		/*
			Funcao: Retorno de msg ao cliente.
			Acao: pode escolher a forma ou padrao da msg
			Obs:
		*/
		if ( is_object($p_info) or is_array($p_info) )		
			echo json_encode($p_info);
		else{
			echo $p_info;
		}
	}
	function is_number($var){
		if ($var == (string) (float) $var) {
			return (bool) is_numeric($var);
		}
		if ($var >= 0 && is_string($var) && !is_float($var)) {
			return (bool) ctype_digit($var);
		}
		return (bool) is_numeric($var);
	}	
	
//###################################
//			MAIN 
//###################################
	
	

	function Principal_post_obtem_arquivo(){
		/*
			Funcao: Obtem arquivo passado para a API.
			Acao: Dependendo da forma como o arquivo é enviado, existe uma forma de captura.
			Obs:
				Pode ser implementado tamanho max ou min, extensões validadas, etc.
		*/
		
		$obj = new Arquivo();	
		
		if (! empty($_FILES['f_file']['name']) ) {
			
			$obj->valido = true;
			$obj->nome = 	$_FILES['f_file']['name'];
			$exploding =	explode(".",$obj->nome);
			$obj->ext = 	end($exploding);
			$obj->size =	$_FILES["f_file"]["size"];
			$obj->fileName =	$_FILES["f_file"]["tmp_name"];
			$obj->info =	file_get_contents($_FILES['f_file']['tmp_name']);
			
			
			//	Retorna_error(2,"Não foi enviado um arquivo".$obj->nome);	
		}else{
			Retorna_error(1,"Não foi enviado um arquivo");	
		}
		return $obj ;
	}	
	
	function Principal_post(){
		/*
			Funcao: Implementacao da API Restfull. POST 
			Acao: Obtem arquivo de envio, faz validações e tratamentos.
			Obs:
		*/
		
		//modulo IN
			
			// 1 Obtem arquivo.
				$o_arq = Principal_post_obtem_arquivo();
			// 2 - Obtem obj por ext
				$o_mod_in = Modulo_in_get_obj_by_Type($o_arq);
			// 3 - valida
				$o_mod_in->valida();
			
		// modulo Middleware
			$o_mod_m = new Modulo_middle_obj($o_mod_in->get_obj_validado());			
			$o_mod_m->agruparByDate();
			
			//var_dump($o_mod_m->getArrayResp());
		
		//modulo out;
		
			// 1 - Obtem obj por salvamento
				$o_mod_out = Modulo_out_get_obj_by_Type($o_mod_m->getArrayResp());
			// 3 - valida
				$o_mod_out->save();
		
		
		
		//retorna informacao
			if (POST_RETORNAARRAY == 0 ){
				
				$obj = new stdClass();	
				$obj->msg = POST_msg;
				
				Retorna_info($obj);
			}else{
				Retorna_info($o_mod_out->get());
			}
	}
	
	function Principal_get(){
		
		
			// 1 - Obtem obj por salvamento
				$o_mod_out = Modulo_out_get_obj_by_Type([]);
			// 2 - obtem info
				$obj = $o_mod_out->get()  ;
			// 3- exibe
				Retorna_info($obj);	
		
	}
	
	function Principal (){
		/*
			Funcao: Implementacao da API Restfull.
			Acao: de acordo com a chamada, é direcionada para cada parte.
			Obs:
		*/
		switch ($_SERVER["REQUEST_METHOD"]) {
			case "POST":
				Principal_post();
				break;
			case "GET":
				Principal_get();
				break;
			case "PUT":
			case "DELETE":
			default:
				Retorna_error(1,"Função n implementada");	
		}
	}			
	
	
	//chamada da funcao principal
		Principal();
?>