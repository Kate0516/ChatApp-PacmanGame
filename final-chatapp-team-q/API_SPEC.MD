# API SPEC

## Template
### RESTful API template

	{“Path”: all path should follow the standard where a noun is followed by a verb 
	to describe user case (except for GET where parameter will be passed in URL) 

	details:{
		Method (get, post):
		
			“description”:,
            {"Request":
                {“Payload”:{
                    {parameters (all parameters are REQUIRED):}
                }}
			},
			{“Response”:
				{“code”:,
				“success”:
				“description”:
				“payload”:{
				}},
				“cookie”:String,
				{“code”:,
				“success”:
				“description”:
				“payload”:{
				“cookie”:String
				}}
			}
		}
	}
### WebSocket data template:

	{
		“type”: String,
		
		“payload”: {
		
		}
	}

## USE CASE & API 
### 1. Create Account

        {“Path”: “/user/create”,
            details:{
                {"Request":{
                    POST:
                        “description”:”Create new user”
                        “Payload”:{
                            “username”: String,
                            “password”: String,
                            “age”: int,
                            “school”: String, 
                            “interest”: []String
                        }
                }},
                {Response:
                            {“code”: 201,
                            “success”: true,
                            “description”: “Successfully created new user”,
                            “payload”: {
                                “cookie”: String
                            }
                            },
                            {“code”: 409 ,
                            “success”:false,
                            “description”: ”Invalid username. (Already exists or Does not meet the requirement)”,
                            “payload”: {
                            }
                            }
                }
            }
        }
For requests in 2-11, and 12, all requests should contain a cookie.

### 2. Present user’s info:
   
        {“Path”: “/user/{username}”
            details:{
                GET:
                    “description”:”get user info”,
                    {"Request":{
                        “Payload”:{
                        }
                        “cookie”: String,
                    }},
                    {“Response”:
                        {“code”:200,
                        “success”: true
                        “description”:”Successfully retrieve user info.”
                        “payload”:{
                            “username”: String,
                            “age”: int,
                            “school”: String, 
                            “interest”: []String,
                            “chatrooms”:[]json(Chat room json)
                        }},
                        {“code”:403,
                        “success”:false
                        “description”:”Not authorized”
                        “payload”:{
                        }},
                        {“code”:404,
                        “success”:false
                        “description”:”User not found”
                        “payload”:{
                        }}
                    }
            }
        }

### 3. Create Chat Room:

        {“Path”: “/chatroom/create”
            details:{
                post:
                    “description”:”Create a new chat room”,
                    {"Request":{
                        “Payload”:{
                            "size": int,
                            "chatroom_name": String,
                            "is_private": bool
                        }
                        “cookie”: String,
                    }},
                    {“Response”:
                        {“code”:200,
                        “success”:true
                        “description”:”Create chat room success”
                        “payload”:{
                            “chatroom_id”: int
                        }},
                        {“code”:403,
                        “success”:false
                        “description”:”Unauthorized, user is not authorized to create chat room”
                        “payload”:{
                        }},
                        {“code”:400,
                        “success”:false
                        “description”:”Chat room size is not valid”
                        “payload”:{
                        }},
                    }
                }
            }
        }

### 4. Join chat room
### 5. Invite user to chat room:
### The two use case (4&5) may be merged into one as "add user into chatroom," where initiater and added user can be the same

        {“Path”: “/chatroom/join”
            details:{
                POST:
                    “description”: “User join a chat room”,
                    {"Request":{
                        “Payload”:{
                            “Invitee(username)”: String,
                            “chatroom_id”:int,
                        },
                        “cookie”:String,
                    }},
                    {“Response”:
                        {“code”: 200,
                        “success”: true
                        “description”: “successfully joined chatroom”
                        “payload”:{
                        }},
                        {“code”: 401,
                        “success”: false
                        “description”: “user is not logged in / Unrecognized user”
                        “payload”:{
                        }},
                        {“code”: 403,
                        “success”: false
                        “description”: “chatroom has private access”
                        “payload”:{
                        }},
                        {“code”: 404,
                        “success”: false
                        “description”: “chatroom not found”
                        “payload”:{
                        }},
                        {“code”: 409,
                        “success”: false
                        “description”: “chatroom had reached maximum size”
                        “payload”:{
                        }}
                    }
            }
        }

### 6. Leave Chat room (SAME AS 7):
### 7. Delete/Ban User in chatroom:
### The two use case (6&7) may be merged into one as "user leave chatroom," where initiater and removed user may or may not be the same

```
{“Path”: “/chatroom/{chatroom_id}/member/{username}”
details:{
    DELETE:
    “description”: “Remove a user from a chat room”,
    {"Request":{
        “Payload”:{
        },
        “cookie”:String
    }},
    {“Response”:

      {“code”: 200,
        “success”: true
        “description”: “successfully removed user”
        “payload”:{
        }},

      {“code”: 401,
        “success”: false
        “description”: “user is not logged in / Unrecognized user”
        “payload”:{
        }},

      {“code”: 403,
        “success”: false
        “description”: “user does not have enough privilege”
        “payload”:{
        }},

      {“code”: 404,
        “success”: false
        “description”: “chatroom not found / user is not in given room”
        “payload”:{
        }}
      }
    }
  }
}
```

  


### 8. View exist user in a chatroom:

```
{“Path”:/chatroom/{chatroom_id}/members
  details:{
    GET:
    “description”:”Get all members in a chat room”,
    {"Request":{
        “Payload”:{}
        “cookie”: String,
    }},
    {“Response”:
      {“code”:200,
      “success”true:
      “description”:”Successfully find all members in a chat room”
      “payload”:{
        []User{
          “username”: String,
          “school”: String,
          “age”: int,
          “interests”:[]String
        }
      }},

      {“code”: 403,
        “success”:false
        “description”:”Unauthorized: user is not authorized to view all members in a chat room.”
        “payload”:{}},

      {“code”: 404,
        “success”:false
        “description”:”Chat room not found”
        “payload”:{}}
      }
  }
}
```

  

### 9. Send Message
Client -> Server:

```
{
  “type”:”send_message”,
  “cookie”: String,
  “payload”: {
    chatroom_id: Int,
    target: “all” || String( username for direct message)
    content: String
  }
}
```

  


### 10. Receive Message:
Server -> Client:
```
{
“type”:”new_message”,
“payload”: {
  chatroom_id: Int,
  target: “all” || String( username for direct message)
  content: String,
  sender: String,
  message_id: Int
}
}
```

    

### 11. Delete Message
Client -> Server:

```
{
  “type”:”delete_message”,
  “cookie”: String,
  “payload”: {
    message_id: Int
  }
}
```



Server -> Client:

```
{
  “type”:”delete_message”,
  “payload”: {
    message_id: Int
  }
}
```
    
###  12.Login Registered User: 
 
        {“Path”: “/user/login”,
            details:{
                POST:
                    “description”:”Login Registered User”
                    {"Request":{
                        “Payload”:{
                            “username”: String,
                            “password”: String,
                        }
                    }},
                    {Response:
                            {“code”: 200,
                            “success”: true,
                            “description”: “Successfully Logged in”,
                            “payload”: {
                                “cookie”: String
                            }
                            },
                            {“code”: 403 ,
                            “success”:false,
                            “description”: ”Invalid username or password”,
                            “payload”: {
                            }
                            }
                    }
            }
        }

### 13.Edit Message: 
Client -> Server:

```
{
  “type”:”edit_message”,
  “cookie”: String,
  “payload”: {
    content: String,
    message_id: Int
  }
}
```



Server -> Client:

```
{
  “type”:”edit_message”,
  “payload”: {
    content: String,
    message_id: Int
  }
}
```
### 14. System Warning and Notification
Server -> Client
```
{
    "type":"notification",
    "payload": {
        "level": "warning" | "info",
        "message": String
    }
}
```


### 15. Show all chat rooms
```
{“Path”: “/chatroom”
    details:{
        GET:
            “description”:”get all chatrooms”,
            {"Request":{
                “Payload”:{
                }
                “cookie”: String,
            }},
            {“Response”:
                {“code”:200,
                “success”: true
                “description”:”Successfully retrieve user info.”
                “payload”:{
                    []Chatroom: {
                        chatroom_id: Int,
                        chatroom_name: string,
                        chatroom_size: Int,
                        is_private: bool,
                        admins: []String,
                        users: []String
                 
                    }
                }},
                {“code”:401,
                “success”:false
                “description”:”user is not logged in / Unrecognized user”
                “payload”:{
                }}
            }
    }
}
```

### 16. Register the current session when user login
Client -> Server
```
{
    "type":"register_session",
    "cookie": String,
}
```