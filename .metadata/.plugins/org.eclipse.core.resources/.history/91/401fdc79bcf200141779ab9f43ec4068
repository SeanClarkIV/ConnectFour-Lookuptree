<?php 
session_start();
if(!isset($_SESSION["sess_user"])){
	header("location:login.php");
} else {
?>

<!DOCTYPE html>
<html>
<head>
<title>User Page</title>

<style type="text/css" media="all">
@import "images/style.css";
</style>





</head>
<body>


<div class="content">





  <div id="submenu">
    
      
    <a href="http://tcnj.edu/"><br>TCNJ Homepage</a>
    </div>
    
    
  <div class="center12">
    <div class="title"><h2>Welcome, <?=$_SESSION['sess_user'];?>! 
    </h2><br></div>
    
  </div>
  <div class="nav">
    <ul>
      <li><a href="index.php">Home</a> | </li>
      <li><a href="logout.php">Logout</a> | </li>
    </ul>
  </div>
 

<div class="center12">
 <br> 
<br>
   <h1>Search Groups</h1> 
	    <p>You  may search either by depatment or subject<p> 
	    <form  method="post" action="searchresults.php"> 
	      <input  name="search" type="text" size="40" maxlength = "50"> 
	      <input  type="submit" name="submit" value="Search"> 
    </form> 
</div>












</body>
</html>



<?php
}



