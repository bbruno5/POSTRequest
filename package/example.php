<?PHP

error_reporting(0); // Don't show errors or warnings, to not pollute console debug
header('Access-Control-Allow-Methods: POST'); // Only accept POST requests
header('Content-type: text/html; charset=UTF-8'); // Always set up unicode, always!

$checkpass = "yourPasswordHere"; // Your password here
$hashAlgorithm = "sha512"; // Declare the hash type

$receivedHash = $_POST['hash']; // The hash data, will always named 'hash'
// Each argument received from plugin is named as arg<number>, so if you have 2 args, they will be arg0 and arg1
if (isset($_POST['arg0'])):
	$arg0 = $_POST['arg0'];
endif;
if (isset($_POST['arg1'])):
	$arg1 = $_POST['arg1'];
endif;
if (isset($_POST['arg2'])):
	$arg2 = $_POST['arg2'];
endif;
if (isset($_POST['arg3'])):
	$arg3 = $_POST['arg3'];
endif;
if (isset($_POST['arg4'])):
	$arg4 = $_POST['arg4'];
endif;
if (isset($_POST['arg5'])):
	$arg5 = $_POST['arg5'];
endif;
// You can set up more variables here, case you need receive more data post

if($receivedHash != "") {
    if($receivedHash == hash($hashAlgorithm, $checkpass)) {
        print_r("Passwords match! Saving data.\n");
        
        //Put your code here.
        print_r("All saved.");
        //Stop editing here.
    }
    else {
        print_r("Passwords don't match!");
    }
}
else {
    print_r("Password data null! Did you really configured one?");
}

?>
