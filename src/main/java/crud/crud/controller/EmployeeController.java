package crud.crud.controller;

import crud.crud.exception.ResourceNotFoundException;
import crud.crud.model.Employe;
import crud.crud.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
     @Autowired
    private EmployeeRepository employeeRepository;

     //getAll request
    @GetMapping("/employes")
    public List<Employe> getAllEmployee() {
         return employeeRepository.findAll();
     }

     //post request
    @PostMapping("/employes")
     public Employe createEmploye(@RequestBody Employe employe)  {
        return  employeeRepository.save(employe);
     }
     //get by Id
    @GetMapping("/employes/{id}")
     public ResponseEntity <Employe> getEmployeById(@PathVariable Long id) {
        Employe employe = employeeRepository.findById(id)
                .orElseThrow(()
                -> new ResourceNotFoundException("Erreur de recuperation de l'employé : " + id));
        return  ResponseEntity.ok(employe);
    }
    //update
    @PutMapping("/employes/{id}")
    public ResponseEntity<Employe> updateEmployee(@PathVariable Long id ,@RequestBody Employe employeDetails) {
        Employe employe = employeeRepository.findById(id).orElseThrow(()
                        -> new ResourceNotFoundException("Erreur de recuperation de l'employé : " + id));

        employe.setFirstname(employeDetails.getFirstname());
        employe.setLastname(employeDetails.getLastname());
        employe.setEmail(employeDetails.getEmail());

        Employe updatedEmploie =  employeeRepository.save(employe);
        return ResponseEntity.ok(updatedEmploie);
    }

    //delete request
    @DeleteMapping("/employes/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id ){
        Employe employe = employeeRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Erreur de recuperation de l'employé : " + id));

        employeeRepository.delete(employe);
        Map<String , Boolean> response  = new HashMap<>();
        response.put("deleted" , Boolean.TRUE);
        return  ResponseEntity.ok(response);
    }
}
