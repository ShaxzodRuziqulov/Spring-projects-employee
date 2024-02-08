package com.example.spring.service.impl;

import com.example.spring.domain.Employee;
import com.example.spring.domain.Task;
import com.example.spring.domain.enumation.Status;
import com.example.spring.domain.request.ReqTask;
import com.example.spring.exceptions.ResourceNotFoundException;
import com.example.spring.repository.EmployeeRepository;
import com.example.spring.repository.TasksRepository;
import com.example.spring.service.TaskService;
import com.example.spring.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TasksRepository tasksRepository;
    private final EmployeeRepository employeeRepository;

    public TaskServiceImpl(TasksRepository tasksRepository, EmployeeRepository employeeRepository) {
        this.tasksRepository = tasksRepository;
        this.employeeRepository = employeeRepository;
    }

    public String createTask(ReqTask reqTask) {
        try {
            Task tasks = new Task();
            tasks.setTitle(reqTask.getTitle());
            tasks.setText(reqTask.getText());
            tasks.setDeadline(reqTask.getDeadline());
            tasks.setStatus(Status.ACTIVE);
            if (reqTask.getEmployee() != null)
                tasks.setEmployee(employeeRepository.findById(reqTask.getEmployee()).orElseThrow(() -> new ResourceNotFoundException("employee")));
            if (reqTask.getCreatedBy() != null) {
                Employee createdBy = employeeRepository.findById(reqTask.getCreatedBy()).orElseThrow(() -> new ResourceNotFoundException("employee"));
                tasks.setCreatedBy(createdBy);
                tasks.setUpdatedBy(createdBy);
            }
            tasksRepository.save(tasks);
            return "Muvoffaqiyatli saqlandi!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Xatolik";
        }
    }

    public String update(ReqTask reqTask) {
        try {
            if (reqTask.getId() != null) {
                Employee updatingEmployee = employeeRepository.findById(reqTask.getUpdateBy()).orElseThrow(() -> new ResourceNotFoundException("employee"));
                Task currentTask = tasksRepository.findById(reqTask.getId()).orElseThrow(() -> new ResourceNotFoundException("task"));
                if (currentTask.getCreatedBy().getId().equals(updatingEmployee.getId()) || Utils.checkPosition(updatingEmployee.getPosition(), currentTask.getCreatedBy().getPosition())) {
                    currentTask.setTitle(reqTask.getTitle());
                    currentTask.setText(reqTask.getText());
                    currentTask.setDeadline(reqTask.getDeadline());
                    if (reqTask.getStatus() != null)
                        currentTask.setStatus(Status.valueOf(reqTask.getStatus()));
                    if (reqTask.getEmployee() != null || !reqTask.getEmployee().equals(currentTask.getEmployee().getId()))
                        currentTask.setEmployee(employeeRepository.findById(reqTask.getEmployee()).orElseThrow(() -> new ResourceNotFoundException("employee")));
                    if (reqTask.getUpdateBy() != null)
                        currentTask.setUpdatedBy(updatingEmployee);
                    tasksRepository.save(currentTask);
                    return "Muvoffaqiyatli saqlandi!";
                } else {
                    return "Bunday topshiriq bazaga kiritilmagan";
                }
            } else {
                return "Topshiriq tanlanmadi";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Xatolik";
        }
    }

    public List<Task> getAllTasks() {
        List<Task> all = tasksRepository.findAll();
        return all;
    }

    public Task getById(Long id) {
        Optional<Task> byId = tasksRepository.findById(id);
        if (byId.isPresent()) {
            Task tasks = byId.get();
            return tasks;
        } else {
            return new Task();
        }
    }

    public void delate(Long id) {
        Task tasks = tasksRepository.getReferenceById(id);
        tasksRepository.delete(tasks);
    }
}
