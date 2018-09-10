<?php   
$dbhost = 'db.soic.indiana.edu';
$dbuser = 'i494f17_team29';
$dbpass = 'my+sql=i494f17_team29';
$dbdata = 'i494f17_team29';
$conn = mysql_connect($dbhost, $dbuser, $dbpass);

$username = $_POST["username"];
$password = $_POST["password"];



if(! $conn ) {
   die('Could not connect: ' . mysql_error());
}

if($username == "" || $password == ""){
   echo "Fill Out All Fields";
}
else{

   $sql = "SELECT * FROM driver WHERE username = '$username' AND password = '$password'";
   mysql_select_db('i494f17_team29');

   $retval = mysql_query($sql, $conn);

   if(! $retval ) {
      die('Could not get data: ' . mysql_error());
   }

   if(mysql_num_rows($retval) > 0){
      while ($row = mysql_fetch_assoc($retval)) {
      echo $row["id"];
   }
 }
 else{
   echo "wrong username or password";
 }
}

mysql_close($conn);
?>