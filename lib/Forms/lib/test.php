 <?php
 $dbhost = 'db.soic.indiana.edu';
 $dbuser = 'i494f17_team29';
 $dbpass = 'my+sql=i494f17_team29';
 $dbdata = 'i494f17_team29';
 $conn = mysql_connect($dbhost, $dbuser, $dbpass);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}


$sql = "SELECT * FROM client";
mysql_select_db('i494f17_team29');
$result = mysql_query($sql, $conn);

if (mysql_num_rows($result) > 0) {
    while ($row = mysql_fetch_assoc($result)) {

        echo $row['fname'];

    }
}
else{
    echo "no results";
}

?>
