<?php   
   $dbhost = 'db.soic.indiana.edu';
   $dbuser = 'i494f17_team29';
   $dbpass = 'my+sql=i494f17_team29';
   $dbdata = 'i494f17_team29';
   $conn = mysql_connect($dbhost, $dbuser, $dbpass);
 
   if(! $conn ) {
      die('Could not connect: ' . mysql_error());
   }
     

$fname = $_POST['fname'];
$lname = $_POST['lname'];
$username = $_POST['username'];
$street = $_POST['street'];
$city = $_POST['city'];
$state = $_POST['state'];
$zip = $_POST['zip'];
$licenseNumber = $_POST['licensenumber'];
$licensePlate = $_POST['licensePlate'];
$expireDate = $_POST['expireDate'];
$lastDrug = $_POST['lastDrug'];
$nextDrug = $_POST['nextDrug'];
$phone = $_POST['phone'];
$email = $_POST['email'];
$password = $_POST['password'];
$confirm = $_POST['confirm'];

/*
$fname = "Test";
$lname = "Farris";
$username = "jordfarr";
$street = "1306 N Maple";
$city = "Bloomington";
$state = "IN";
$zip = 47404;
$licenseNumber = 1982343243;
$licensePlate = "X7MG81";
$exireDate = "2021-4-1";
$lastDrug = "2016-5-1";
$nextDrug ="2020-5-1";
$phone = "8129872973";
$email = "jordfarr@indiana.edu";
$password = "sniffles";
$confirm = "sniffles";
*/


//INSERT DATA INTO DRIVER DATABASE
$sql = "INSERT INTO driver (fname, lname, email, street, city, state, zip, license, expiration, plate, lastDrug, nextDrug, phone, username, password)
		VALUES ('$fname', '$lname', '$email', '$street', '$city', '$state', '$zip', '$licenseNumber', '$expireDate', '$licensePlate', '$lastDrug', '$nextDrug', '$phone', '$username', '$password')";

mysql_select_db('i494f17_team29');

 $retval = mysql_query( $sql, $conn);
     
      if(! $retval ) {
         die('Could not get data: ' . mysql_error());
      }
      else{
         echo("New Driver added to Database");
      }
      
mysql_close($conn);
?>
