<?php
if ($_SERVER['REQUEST_METHOD'] == 'DELETE') {
    
    $clienteID = $_GET['clienteID'];
    
    if ($token == "xpto" && !is_null($clienteID)) {
        include_once "dbConnection.php";
		
        $sql = "DELETE cliente   
				 WHERE id = ?";
        $statement = $pdo->prepare($sql);

		$statement->bindParam(1, $clienteID;		

        $statement->execute();
       // $results = $statement->fetchAll(PDO::FETCH_ASSOC);
        //echo "<pre>".print_r($results)."</pre"; 
        //echo$json = json_encode($results);
        echo "Registro excluído com sucesso";
    
    }else{
        echo "Não autorizado.";
    }
} else {
    echo "Acesso Negado.";
}
?>