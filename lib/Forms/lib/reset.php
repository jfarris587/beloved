<?php   
   $dbhost = 'db.soic.indiana.edu';
   $dbuser = 'i494f17_team29';
   $dbpass = 'my+sql=i494f17_team29';
   $dbdata = 'i494f17_team29';
   $conn = mysql_connect($dbhost, $dbuser, $dbpass);

   $fname = $_POST["fname"];
   $lname = $_POST["lname"];
   $email = $_POST["email"];

   if(! $conn ) {
      die('Could not connect: ' . mysql_error());
   }


$sql = "SELECT username, password FROM driver WHERE fname = '$fname' AND lname='$lname' AND email = '$email'";
   mysql_select_db('i494f17_team29');
   $retval = mysql_query( $sql, $conn);

   if(mysql_num_rows($retval) != 0){
      //Driver Exists
      

      $row = mysql_fetch_row($retval);
      $username = $row[0];
      $password = $row[1];



      $to = $email;
      $subject = "BELOVED TRANSPORTATION: Forgot Password";
      $headers = "From: contact@belovedtransportation.com";
      $body = "Hello $fname, \n\n you are a driver for Beloved Transportation and forgot your password. To get access to your account, remember your username and password below... \n\n
         USERNAME: " . $username .
         "\n\n
         PASSWORD: " . $password;

      if(!mail($to,$subject,$body,$headers)){
       echo "Email failed to send. Please try again later...";
      }
      else{
         echo "Please check email";
      }
   }

   else{
      echo "Driver Doesn't Exist";
   }
   
   mysql_close($conn);

   ?>