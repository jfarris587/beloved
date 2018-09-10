<?php   
$dbhost = 'db.soic.indiana.edu';
$dbuser = 'i494f17_team29';
$dbpass = 'my+sql=i494f17_team29';
$dbdata = 'i494f17_team29';
$conn = mysql_connect($dbhost, $dbuser, $dbpass);



if(! $conn ) {
   die('Could not connect: ' . mysql_error());
}

$driver = $_POST['driver'];
$request = $_POST['request'];

//INSERT DATA INTO DRIVER DATABASE
$sql = "SELECT * pickup 
	WHERE request= '$request'";

mysql_select_db('i494f17_team29');

$retval = mysql_query($sql, $conn);

if(mysql_num_rows($retval) != 0){
	echo "Reuqest already scheduled. Delete from schedule to add to driver";
}

else{
	//INSERT DATA INTO DRIVER DATABASE
	$sql = "INSERT INTO pickup (driver, request)
	VALUES ($driver, $request)";

	mysql_select_db('i494f17_team29');

	$retval = mysql_query($sql, $conn);

	if(! $retval ) {
	   die('Could not get data: ' . mysql_error());
	}
	else{
	   echo("New Request Added to Databse");
	}
}


?>

