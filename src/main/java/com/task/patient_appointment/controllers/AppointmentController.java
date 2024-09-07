package com.task.patient_appointment.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.task.patient_appointment.Dto.AppointmentDto;
import com.task.patient_appointment.models.Appointment;
import com.task.patient_appointment.services.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController extends BaseController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Operation(description = "Get Today's Appointments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")})
    @GetMapping(path = "/today", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentDto>> getTodayAppointments() {
        return new ResponseEntity<>(appointmentService.getAllTodayAppointments(), HttpStatus.OK);
    }

    @Operation(description = "Add new Appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201")})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentDto> addNewAppointment(@RequestBody @Valid AppointmentDto appointment) {
        AppointmentDto appointmentDto = appointmentService.addAppointment(modelMapper.map(appointment, Appointment.class));
        return new ResponseEntity<>(appointmentDto, HttpStatus.CREATED);
    }


    @Operation(description = "Get Future Appointments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")})
    @GetMapping(path = "/future", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentDto>> getFutureAppointments(@RequestParam("date") long date) {
        return new ResponseEntity<>(appointmentService.getAllFutureAppointments(date), HttpStatus.OK);
    }

    @Operation(description = "Get Past Appointments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")})
    @GetMapping(path = "/past", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentDto>> getPastAppointments(@RequestParam("date") long date) {
        return new ResponseEntity<>(appointmentService.getAllPreviousAppointments(date), HttpStatus.OK);
    }

    @Operation(description = "Get Patient Appointments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Patient Not Found")
    })
    @GetMapping(path = "/patients/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentDto>> getPatientAppointments(@PathVariable Long patientId) {
        return new ResponseEntity<>(appointmentService.getAllPatientAppointments(patientId), HttpStatus.OK);
    }

    @Operation(description = "Get Patient Appointments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Invalid Search Criteria")})
    @GetMapping(path = "/patients", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentDto>> searchByPatientName(@RequestParam("name") String name) {
        return new ResponseEntity<>(appointmentService.searchByPatientName(name), HttpStatus.OK);
    }

    @Operation(description = "Cancel Appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Appointment Not Found")})
    @DeleteMapping(path = "/{appointmentId}")
    public ResponseEntity<ObjectNode> cancelAppointment(@PathVariable Long appointmentId,
                                                        @RequestBody CancelAppointmentRequest cancelAppointmentRequest) {
        appointmentService.cancelAppointment(appointmentId, cancelAppointmentRequest.cancellationReason);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode tokenJson = objectMapper.createObjectNode();
        tokenJson.put("message", "Cancelled Successfully");
        return new ResponseEntity<>(tokenJson, HttpStatus.OK);
    }

    @Data
    @NoArgsConstructor
    private static class CancelAppointmentRequest {
        @Size(max = 255, message = "Cancellation Reason cannot be more than 255 characters")
        private String cancellationReason;
    }
}
