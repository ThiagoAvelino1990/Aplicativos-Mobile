<?php
if($_SERVER['REQUEST_METHOD'] == 'GET'){

    $token = $_GET['token'] ?? null;
    $clienteID = $_GET['clienteID'] ?? null;

    if($token == "xpto" && !is_null($clienteID)){
        include_once "dbConnection.php";
        $sql = "SELECT * FROM cliente WHERE id = ?";

        $statement = $pdo->prepare($sql);
        $statement->bindParam(1,$clienteID);
        $statement->execute();

        $results = $statement->fetchAll(PDO::FETCH_ASSOC);
        //echo "<pre>".print_r($results)."</pre"; 
        $json = json_encode($results);
        echo $json;
    }else if($token == "xpto"){
        include_once "dbconnection.php";
        $sql = "SELECT * FROM cliente";
        $statement = $pdo->prepare($sql);        
        $statement->execute();
        $results = $statement->fetchAll(PDO::FETCH_ASSOC);
        //echo "<pre>".print_r($results)."</pre"; 
        $json = json_encode($results);
        echo $json;
    }else{
        echo "Não Autorizado";
    }

}else{
    echo "Opção inválida";
}

?>