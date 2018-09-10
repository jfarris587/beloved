<!DOCTYPE html>
<html lang="en">
<head>
  <title>New Driver</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="font-awesome-4.6.3/css/font-awesome.min.css">
  <link href="https://fonts.googleapis.com/css?family=Nunito+Sans:300,600|Raleway:100,300,400|Roboto:100" rel="stylesheet">
  <link rel="icon" type="image/png" href="img/icon.png" sizes="16x16">
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <link rel="stylesheet" type="text/css" href="style.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

  <script type="text/javascript">
    window.onload = prepareButton;

    var required = ["firstname",
                    "lastname",
                    "email",
                    "street",
                    "city",
                    "zip",
                    "licensenumber",
                    "platenumber",
                    "phone",
                    "password",
                    "confirmpassword"]

    var submitReady = false;

    function prepareButton()
    { 
       document.getElementById('submitbtn').onclick = function()
       {

        submitReady = true;

        if(document.getElementById('password').value != document.getElementById('confirmpassword').value){
          alert('Passwords Do Not Match');
          return false;
        }
        
        for(var i = 0; i < required.length; i++){
          if(document.getElementById(required[i]).value == ""){
            document.getElementById(required[i]).className += " invalid";
            submitReady = false;
          }
        }  

        if(submitReady){
          document.getElementById("registrationForm").submit();
        }

       }
    }

    
   

  </script>
</head>

<body>


  <!-- NAVIGATION BAR -->
  <div id="myNavbar" class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
      </div>  

      <div class="navbar-collapse collapse">
        <ul class="nav navbar-nav navbar-right">
          <li><a href="home.html">Home</a></li>
          <li><a href="driver.php">Add Driver</a></li>
          <li><a href="client.php">Add Client</a></li>
          <li><a href="request.php">Request Pickup</a></li>
          <li><a href="schedule.php">Schedule Pickup</a></li>
        </ul>
      </div> 
    </div>
  </div>
  <!-- END NAVIGATION BAR -->

  <!-- MAIN FORM CONTAINER -->
  <div class="container overall">
    <div class="row">
      <div class="col-md-8 col-sm-8">

        <h3 class="header"><span>Adding New Driver</span></h3>

        <form id="registrationForm" action="lib/driver.php" method="post">
          <div class="form-row">
            <div class="col-md-4 col-sm-4">
              <label>First Name</label>
              <input type="text" class="form-control" name="fname" id="firstname" placeholder="First Name">
            </div>
            <div class="col-md-4 col-sm-4">
              <label>Last Name</label>
              <input type="text" class="form-control" name="lname" id="lastname" placeholder="Last Name">
            </div>
            <div class="col-md-4 col-sm-4">
              <label>Email</label>
              <input type="text" class="form-control" name="email" id="email" placeholder="Email">
            </div>
          </div>

    
          <div class="form-row">
            <div class="col-md-2 col-sm-2">
             <label>Street</label>
             <input type="text" class="form-control" name="street" id="street" placeholder="Street">
           </div>

            <div class="col-md-4 col-sm-4">
             <label>City</label>
             <input type="text" class="form-control" name="city" id="city" placeholder="City">
           </div>

           <div class="col-md-2 col-sm-2">
            <label>State</label>
            <select class="form-control" name="state">
              <option selected>IN</option>
              <option value="AL">AL</option>
              <option value="AK">AK</option>
              <option value="AR">AR</option>  
              <option value="AZ">AZ</option>
              <option value="CA">CA</option>
              <option value="CO">CO</option>
              <option value="CT">CT</option>
              <option value="DC">DC</option>
              <option value="DE">DE</option>
              <option value="FL">FL</option>
              <option value="GA">GA</option>
              <option value="HI">HI</option>
              <option value="IA">IA</option>  
              <option value="ID">ID</option>
              <option value="IL">IL</option>
              <option value="IN">IN</option>
              <option value="KS">KS</option>
              <option value="KY">KY</option>
              <option value="LA">LA</option>
              <option value="MA">MA</option>
              <option value="MD">MD</option>
              <option value="ME">ME</option>
              <option value="MI">MI</option>
              <option value="MN">MN</option>
              <option value="MO">MO</option>  
              <option value="MS">MS</option>
              <option value="MT">MT</option>
              <option value="NC">NC</option>  
              <option value="NE">NE</option>
              <option value="NH">NH</option>
              <option value="NJ">NJ</option>
              <option value="NM">NM</option>      
              <option value="NV">NV</option>
              <option value="NY">NY</option>
              <option value="ND">ND</option>
              <option value="OH">OH</option>
              <option value="OK">OK</option>
              <option value="OR">OR</option>
              <option value="PA">PA</option>
              <option value="RI">RI</option>
              <option value="SC">SC</option>
              <option value="SD">SD</option>
              <option value="TN">TN</option>
              <option value="TX">TX</option>
              <option value="UT">UT</option>
              <option value="VT">VT</option>
              <option value="VA">VA</option>
              <option value="WA">WA</option>
              <option value="WI">WI</option>  
              <option value="WV">WV</option>
              <option value="WY">WY</option>
            </select>
          </div>

          <div class="col-md-2 col-sm-2">
            <label>Zip</label>
            <input type="text" class="form-control" name="zip" id="zip" placeholder="Zip">
          </div>
        </div>


        <div class="form-row">
          <div class="col-md-6 col-sm-6">
           <label>Driver's License Number</label>
           <input type="text" class="form-control" name="licensenumber" id="licensenumber" placeholder="#############">
         </div>

         <div class="col-md-3 col-sm-3">
          <label>Expiration Date</label>
          <input type="date" class="form-control" name="expireDate" id="expiration" placeholder="State">
        </div>

        <div class="col-md-3 col-sm-3">
          <label>License Plate</label>
          <input type="text" class="form-control" name="licensePlate" id="platenumber" placeholder="#######">
        </div>
      </div>


      <div class="form-row">
        <div class="col-md-3 col-sm-3">
         <label>Last Drug Screen</label>
         <input type="date" name="lastDrug" class="form-control">
       </div>

       <div class="col-md-3 col-sm-3">
        <label>Next Drug Screen</label>
        <input type="date" name="nextDrug" class="form-control">
      </div>

      <div class="col-md-6 col-sm-6">
        <label>Phone Number</label>
        <input type="text" class="form-control" name="phone" id="phone" placeholder="#######">
      </div>

      <div class="form-row">
        <div class="col-md-4 col-sm-4">
         <label>Username</label>
         <input type="email" class="form-control" name="username" placeholder="Username">
       </div>

        <div class="col-md-4 col-sm-4">
         <label>Password</label>
         <input type="password" class="form-control" name="password" id="password" placeholder="Password">
       </div>

       <div class="col-md-4 col-sm-4">
        <label>Confirm Password</label>
        <input type="password" class="form-control" name="confirm" id="confirmpassword" placeholder="Confirm Password">
      </div>
    </div>


    <div class="form-row">
     <div class="col-md-3 col-sm-3">
       <button class="btn btn-primary submit" id="submitbtn" type="button">Submit Form</button>
     </div>
   </div>
 </form>
</div>
</div>
<!-- END MAIN FORM CONTAINER -->

<!--  LOGO -->
<div class="col-md-4 col-sm-4 image img-responsive">
  <img class="img-responsive" id="logo" src="img/whitelogo.png">
</div>
<!-- END LOGO -->
</div>
</div>




</body>
</html>