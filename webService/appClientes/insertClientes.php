<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    
    $token = $_GET['token'];    
    $nome = $_GET['nome'];
	$telefone = $_GET['telefone'];
	$email = $_GET['email'];
	$cep = $_GET['cep'];
	$logradouro = $_GET['logradouro'];
	$complemento = $_GET['complemento'];
	$numero = $_GET['numero'];
	$bairo = $_GET['bairro'];
	$cidade = $_GET['cidade'];
	$estado = $_GET['estado'];
	$pais = $_GET['pais'];
	$documento = $_GET['documento'];
	$id_tipo_documento = $_GET['id_tipo_documento'];
	$id_tipo_pessoa = $_GET['id_tipo_pessoa'];
	$termos_de_uso = $_GET['termos_de_uso'];
	$data_inclusoa = $_GET['data_inclusao'];
	$data_alteracao = $_GET['data_alteracao'];
	$id_usuario = $_GET['id_usuario'];    
    
    if ($token == "xpto" && !is_null($id_usuario)) {
        include_once "dbConnection.php";
        $sql = "INSERT INTO cliente (nome, telefone, email, cep, logradouro, complemento, numero, bairro, cidade, estado, pais,
									 documento, id_tipo_documento, id_tipo_pessoa, termos_de_uso, data_inclusao, data_alteracao, id_usuario)									 
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
		

        $statement->execute();
       // $results = $statement->fetchAll(PDO::FETCH_ASSOC);
        //echo "<pre>".print_r($results)."</pre"; 
        //echo$json = json_encode($results);
        echo "Registro incluso com sucesso";
    
    }else{
        echo "Não autorizado.";
    }
} else {
    echo "Acesso Negado.";
}
?>