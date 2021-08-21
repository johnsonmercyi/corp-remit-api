package com.soft.cr.api;

import java.util.HashMap;
import java.util.UUID;

import com.soft.cr.model.User;
import com.soft.cr.service.UserService;
import com.soft.cr.util.CustomMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {
    "http://localhost:3000", 
    // "http://192.168.43.100:3000"
}, maxAge = 3600)

@RequestMapping("api/coore/v1/user")
@RestController
public class UserController {
    
    private final UserService service;

    /**
     * @param service
     */
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public Object read () {
        Object response = null;
		try {
			response = service.read();
		}catch (Exception ex) {
            System.out.println(ex.getClass());
            ex.printStackTrace();
			
			
			if (ex instanceof CannotGetJdbcConnectionException) {
				response = new CustomMessage(true, "Server Error", "Database Connection Lost", "The Service(s) required by your SQL Database Server to run might not have been started.");
			} else {
				response = new CustomMessage(true, "Server Error", ex.getClass().getSimpleName(), "Not yet resolved!");
			}
			
		}
		return response;
	}

    @GetMapping(path="{id}")
    public Object read (@PathVariable("id") UUID id) {
        Object response = null;
		
		try {
			response = service.read(id);
		}catch (Exception ex) {
            System.out.println(ex.getClass());
			System.out.println(ex.getClass());
			
			if (ex instanceof CannotGetJdbcConnectionException) {
				response = new CustomMessage(true, "Server Error", "Database Connection Lost", "The Service(s) required by your SQL Database Server to run might not have been started.");
			} if (ex instanceof EmptyResultDataAccessException) {
				response = new CustomMessage(true, "Invalid User", "User Credential is Invalid", "");
			} else {
				response = new CustomMessage(true, "Server Error", ex.getClass().getSimpleName(), "Not yet resolved!");
			}
			
		}
		return response;
	}

    @PostMapping
    public int insert (@RequestBody User user) {
        int response = 0;
        try {
            response = service.insert(user);
        } catch (Exception e) {
            //TODO: handle exception
        }
        return response;
    }

    @PutMapping(path="{id}")
    public int update (@PathVariable("id") UUID id, @RequestBody User user) {
        int response = 0;

        try {
            response = service.update(id, user);
        } catch (Exception e) {
            //TODO: handle exception
        }
        return response;
    }

    @DeleteMapping(path="{id}")
    public int delete (@PathVariable("id") UUID id) {
        int response = 0;

        try {
            response = service.delete(id);
        } catch (Exception e) {
            //TODO: handle exception
        }
        return 0;
    }

}
