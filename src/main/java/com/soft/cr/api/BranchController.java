package com.soft.cr.api;

import java.util.UUID;

import com.soft.cr.model.Branch;
import com.soft.cr.service.BranchService;
import com.soft.cr.util.CustomMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

@CrossOrigin(origins = { "http://localhost:3000",
        // "http://192.168.43.100:3000"
}, maxAge = 3600)

@RequestMapping("api/coore/v1/branch")
@RestController
public class BranchController {
    
    private BranchService service;

    /**
     * @param service
     */
    @Autowired
    public BranchController(BranchService service) {
        this.service = service;
    }

    @GetMapping
    public Object read() {
        Object response = null;
        try {
            response = service.read();
        } catch (Exception ex) {
            System.out.println(ex.getClass());
            ex.printStackTrace();

            if (ex instanceof CannotGetJdbcConnectionException) {
                response = new CustomMessage(true, "Server Error", "Database Connection Lost",
                        "The Service(s) required by your SQL Database Server to run might not have been started.");
            } else {
                response = new CustomMessage(true, "Server Error", ex.getClass().getSimpleName(), "Not yet resolved!");
            }

        }
        return response;
    }

    @GetMapping(path = "{id}")
    public Object read(@PathVariable("id") UUID id) {
        Object response = null;

        try {
            response = service.read(id);
        } catch (Exception ex) {
            System.out.println(ex.getClass());

            if (ex instanceof CannotGetJdbcConnectionException) {
                response = new CustomMessage(true, "Server Error", "Database Connection Lost",
                        "The Service(s) required by your SQL Database Server to run might not have been started.");
            } else if (ex instanceof EmptyResultDataAccessException) {
                response = new CustomMessage(true, "Invalid Branch", "Branch Credential is Invalid", "");
            } else {
                response = new CustomMessage(true, "Server Error", ex.getClass().getSimpleName(), "Not yet resolved!");
            }

        }
        return response;
    }

    @PostMapping
    public Object insert(@RequestBody Branch branch) {
        Object response = 0;
        try {
            response = service.insert(branch);
        } catch (Exception ex) {
            System.out.println(ex.getClass());
            ex.printStackTrace();
            System.out.println(ex.getMessage());

            if (ex instanceof CannotGetJdbcConnectionException) {
                response = new CustomMessage(true, "Server Error", "Database Connection Lost",
                        "The Service(s) required by your SQL Database Server to run might not have been started.");
            } else if (ex instanceof EmptyResultDataAccessException) {
                response = new CustomMessage(true, "Invalid Branch", "Branch Credential is Invalid", "");
            } else if (ex instanceof DuplicateKeyException) {
                response = new CustomMessage(true, "Branch name Taken!", "Please choose a different branch name",
                        "");
            } else if (ex.getMessage() == "Error creating branch!") {
                response = new CustomMessage(true, "Error Creating Branch", "Branch creation was unsuccessfull", "");

            } else {
                response = new CustomMessage(true, "Server Error", ex.getClass().getSimpleName(), "Not yet resolved!");
            }
        }
        return response;
    }

    @PutMapping(path = "{id}")
    public Object update(@PathVariable("id") UUID id, @RequestBody Branch branch) {
        Object response = 0;

        try {
            response = service.update(id, branch);
        } catch (Exception ex) {
            System.out.println(ex.getClass());
            ex.printStackTrace();

            if (ex instanceof CannotGetJdbcConnectionException) {
                response = new CustomMessage(true, "Server Error", "Database Connection Lost",
                        "The Service(s) required by your SQL Database Server to run might not have been started.");
            } else if (ex instanceof EmptyResultDataAccessException) {
                response = new CustomMessage(true, "Invalid Branch", "Branch Credential is Invalid", "");
            } else if (ex instanceof DuplicateKeyException) {
                System.out.println(ex.getMessage());
                response = new CustomMessage(true, "Branch name Taken!", "Please choose a different branch name",
                        "");
                
            } else {
                response = new CustomMessage(true, "Server Error", ex.getClass().getSimpleName(), "Not yet resolved!");
            }
        }
        return response;
    }

    @DeleteMapping(path = "{id}")
    public Object delete(@PathVariable("id") UUID id) {
        Object response = 0;

        try {
            response = service.delete(id);
        } catch (Exception ex) {
            System.out.println(ex.getClass());
            ex.printStackTrace();

            if (ex instanceof CannotGetJdbcConnectionException) {
                response = new CustomMessage(true, "Server Error", "Database Connection Lost",
                        "The Service(s) required by your SQL Database Server to run might not have been started.");
            } else if (ex instanceof EmptyResultDataAccessException) {
                response = new CustomMessage(true, "Invalid Branch", "Branch Credential is Invalid", "");
            } else {
                response = new CustomMessage(true, "Server Error", ex.getClass().getSimpleName(), "Not yet resolved!");
            }
        }
        return response;
    }

}
