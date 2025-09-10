<?php
if ($_SERVER['REQUEST_METHOD'] == 'DELETE') {
    
    $clienteID = $_DELETE['ID'];
    
    if ($token == "xpto" && !is_null($clienteID)) {
        include_once "dbConnection.php";
		
        $sql = "DELETE CLIENTE   
				 WHERE ID = ?";
        $statement = $pdo->prepare($sql);

		$statement->bindParam(1, $clienteID;		

        if($statement->execute()){
			echo json_encode(["Status"=>"Sucesso","Mensagem"=>"DELETE realizado com sucesso"]);
		}else{
			$error = $statement->errorInfo();			
			echo json_encode(["Status"=>"Erro", "Mensagem"=>"Erro ao realizar o DELETE ".$error[2]]);			
		} 
       // $results = $statement->fetchAll(PDO::FETCH_ASSOC);
        //echo "<pre>".print_r($results)."</pre"; 
        //echo$json = json_encode($results);        
    
    }else{
        cho json_encode(["Status"=>"Erro", "Mensagem"=>"Não autorizado"]);
    }
} else {
    echo json_encode(["Status"=>"Erro", "Mensagem"=>"Acesso Negado"]);
}
?>