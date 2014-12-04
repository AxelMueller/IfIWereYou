<?php

/**
 * File to handle all API requests
 * Accepts GET and POST
 * 
 * Each request will be identified by TAG
 * Response will be JSON data

  /**
 * check for POST request 
 */
if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // get tag
    $tag = $_POST['tag'];

    // include db handler
    require_once 'include/DB_Functions.php';
    $db = new DB_Functions();

    // response Array
    $response = array("tag" => $tag, "success" => 0, "error" => 0);

    // check for tag type
    if ($tag == 'login') {
        // Request type is check Login
        $email = $_POST['email'];
        $password = $_POST['password'];

        // check for user
        $user = $db->getUserByEmailAndPassword($email, $password);
        if ($user != false) {
            // user found
            // echo json with success = 1
            $response["success"] = 1;
            $response["id"] = $user["id"];
            $response["user"]["firstname"] = $user["firstname"];
			$response["user"]["lastname"] = $user["lastname"];
            $response["user"]["mail"] = $user["mail"];
            $response["user"]["phonenumber"] = $user["phonenumber"];
            echo json_encode($response);
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = 1;
            $response["error_msg"] = "Incorrect email or password!";
            echo json_encode($response);
        }
    } else if ($tag == 'register') {
        // Request type is Register new user
        $firstname = $_POST['firstname'];
		$lastname = $_POST['lastname'];
        $email = $_POST['email'];
        $password = $_POST['password'];
		$phonenumber = $_POST['phonenumber'];

        // check if user is already existed
        if ($db->isUserExisted($email)) {
            // user is already existed - error response
            $response["error"] = 2;
            $response["error_msg"] = "User already existed";
            echo json_encode($response);
        } else {
            // store user
            $user = $db->storeUser($firstname, $lastname, $email, $password, $phonenumber);
            if ($user) {
                // user stored successfully
                $response["success"] = 1;
                $response["id"] = $user["id"];
                $response["user"]["firstname"] = $user["firstname"];
				$response["user"]["lastname"] = $user["lastname"];
				$response["user"]["mail"] = $user["mail"];
				$response["user"]["phonenumber"] = $user["phonenumber"];
                echo json_encode($response);
            } else {
                // user failed to store
                $response["error"] = 1;
                $response["error_msg"] = "Error occurred in Registration";
                echo json_encode($response);
            }
        }
    } else if ($tag == 'addfriend') {
		// Request type ist add a new contact as a friend
		$userid = $_POST['userid'];
		$friendmail = $_POST['friendmail'];
		if(!$db->isUserExisted($friendmail)) {
			$response["error"] = 3;
			$response["error_msg"] = "No user with the given mail address";
			echo json_encode($response);
		}
		else {
			$user = $db->addfriend($userid, $friendmail);
            if ($user) {
                // user stored successfully
                $response["success"] = 1;
                $response["id"] = $user["id"];
                $response["user"]["firstname"] = $user["firstname"];
				$response["user"]["lastname"] = $user["lastname"];
				$response["user"]["mail"] = $user["mail"];
				$response["user"]["phonenumber"] = $user["phonenumber"];
                echo json_encode($response);
            } else {
                // user failed to store
                $response["error"] = 4;
                $response["error_msg"] = "Error occurred while adding contact";
                echo json_encode($response);
            }
		}
	} else if ($tag == 'deleteaccount') {
		$userid = $_POST['userid'];
		echo json_encode($db->deleteaccount($userid));
	}
	else {
        echo "Invalid Request";
    }
} else {
    echo "Access Denied";
}
?>
