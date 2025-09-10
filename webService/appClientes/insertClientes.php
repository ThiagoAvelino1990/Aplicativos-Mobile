<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    
    $token = $_POST['token'];    
    $nome = $_POST['NOME'];
	$telefone = $_POST['TELEFONE'];
	$email = $_POST['EMAIL'];
	$cep = $_POST['CEP'];
	$logradouro = $_POST['LOGRADOURO'];
	$complemento = $_POST['COMPLEMENTO'];
	$numero = $_POST['NUMERO'];
	$bairo = $_POST['BAIRRO'];
	$cidade = $_POST['CIDADE'];
	$estado = $_POST['ESTADO'];
	$pais = $_POST['PAIS'];
	$documento = $_POST['DOCUMENTO'];
	$id_tipo_documento = $_POST['ID_TIPO_DOCUMENTO'];
	$id_tipo_pessoa = $_POST['ID_TIPO_PESSOA'];
	$termos_de_uso = $_POST['TERMOS_DE_USO'];
	$data_inclusoa = $_POST['DATA_INCLUSAO'];
	$data_alteracao = $_POST['DATA_ALTREACAO'];
	$id_usuario = $_POST['ID_USUARIO'];    
    
    if ($token == "xpto" && !is_null($id_usuario)) {
        include_once "dbConnection.php";
        $sql = "INSERT INTO CLIENTE (NOME, TELEFONE, EMAIL, CEP, LOGRADOURO, COMPLEMENTO, NUMERO, BAIRRO, CIDADE, ESTADO, PAIS,
									 DOCUMENTO, ID_TIPO_DOCUMENTO, ID_TIPO_PESSOA, TERMOS_DE_USO, DATA__INCLUSAO, DATA_ALTERACAO, ID_USUARIO)									 
				VALUES( ?, ?, ?, ? , ?, ?, ?, ?, ?, ?, ?,
						?, ?, ?, ?, ?, ?, ?);";
        $statement = $pdo->prepare($sql);
		
		$statement->bindParam(1, $nome);
		$statement->bindParam(2, $telefone);
		$statement->bindParam(3, $email);
		$statement->bindParam(4, $cep);
		$statement->bindParam(5, $logradouro);
		$statement->bindParam(6, $complemento);
		$statement->bindParam(7, $numero);
		$statement->bindParam(8, $bairo);
		$statement->bindParam(9, $cidade);
		$statement->bindParam(10, $estado);
		$statement->bindParam(11, $pais);
		$statement->bindParam(12, $documento);
		$statement->bindParam(13, $id_tipo_documento);
		$statement->bindParam(14, $id_tipo_pessoa);
		$statement->bindParam(15, $termos_de_uso);
		$statement->bindParam(16, $data_inclusoa);
		$statement->bindParam(17, $data_alteracao);
		$statement->bindParam(18, $id_usuario);
		

        if($statement->execute()){
			echo json_encode(["Status"=>"Sucesso","Mensagem"=>"INSERT realizado com sucesso"]);
		}else{
			$error = $statement->errorInfo();			
			echo json_encode(["Status"=>"Erro", "Mensagem"=>"Erro ao realizar o INSERT ".$error[2]]);			
		}   
       // $results = $statement->fetchAll(PDO::FETCH_ASSOC);
        //echo "<pre>".print_r($results)."</pre"; 
        //echo$json = json_encode($results);        
    
    }else{
        echo json_encode(["Status"=>"Erro", "Mensagem"=>"Não autorizado"]);
    }
} else {
    echo json_encode(["Status"=>"Erro", "Mensagem"=>"Acesso Negado"]);
}
?>