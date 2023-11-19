package co.udea.ssmu.api.controller.drivers;

import co.udea.ssmu.api.utils.common.Messages;
import co.udea.ssmu.api.utils.common.StandardResponse;
import co.udea.ssmu.api.model.jpa.dto.drivers.DriverDTO;
import co.udea.ssmu.api.utils.exception.DataBaseException;
import co.udea.ssmu.api.services.drivers.facade.DriverFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@Tag(name = "Drivers", description = "Gestión de conductores")
@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverFacade driverFacade;
    private final Messages messages;

    public DriverController(DriverFacade driverFacade, Messages messages) {
        this.driverFacade = driverFacade;
        this.messages = messages;
    }

    @PostMapping("/save")
    @Operation(summary = "Permite guardar un conductor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = DriverDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            }, description = "El conductor fue guardado exitosamente"),
            @ApiResponse(responseCode = "400", description = "La petición es inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta")})
    public ResponseEntity<StandardResponse<DriverDTO>> save(@Valid @RequestBody DriverDTO driver) {
        return ResponseEntity.ok(new StandardResponse<>(StandardResponse.StatusStandardResponse.OK,
                messages.get("driver.save.successful"),
                driverFacade.save(driver)));
    }

    @GetMapping("/get-all")
    @Operation(summary = "Permite consultar todos los conductores")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = List.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            }, description = "Los conductores fueron consultados exitosamente"),
            @ApiResponse(responseCode = "400", description = "La petición es inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta")})
    public ResponseEntity<StandardResponse<List<DriverDTO>>> findAll() {
        return ResponseEntity.ok(new StandardResponse<>(StandardResponse.StatusStandardResponse.OK,
                messages.get("driver.get.all.successful"),
                driverFacade.findByAll()));
    }

    @GetMapping("/get-all/filter")
    @Operation(summary = "Consultar los conductores paginado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Los conductores fueron consultados exitosamente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Page.class)))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta")
    })
    public ResponseEntity<StandardResponse<Page<DriverDTO>>> getWithPage(
            @Parameter(description = "Página para la cual se desean recibir los resultados (0..N)")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Número de registros por página.")
            @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "Criterio de ordenamiento en el formato: campo(,asc|desc). "
                    + "por defecto el ordenamiento es asc. "
                    + "Se permite múltiple criterio de ordenamiento.")
            @RequestParam(defaultValue = "id,asc") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc(sort.split(",")[0])));

        return ResponseEntity.ok(new StandardResponse<>(StandardResponse.StatusStandardResponse.OK,
                messages.get("driver.get.all.filter.successful"),
                driverFacade.getWithPage(pageable)));
    }

    @PutMapping("/update")
    @Operation(summary = "Permite actualizar un conductor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = DriverDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            }, description = "El país fue habilitado exitosamente"),
            @ApiResponse(responseCode = "400", description = "La petición es inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta")})
    public ResponseEntity<StandardResponse<DriverDTO>> update(@Valid @RequestBody DriverDTO driver) {
        return ResponseEntity.ok(new StandardResponse<>(StandardResponse.StatusStandardResponse.OK,
                messages.get("driver.update.successful"),
                driverFacade.update(driver)));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Permite eliminar un conductor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "El conductor fue eliminada exitosamente"),
            @ApiResponse(responseCode = "400", description = "La petición es inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta")})
    public ResponseEntity<StandardResponse<Void>> delete(@PathVariable Integer id) {
        try {
            driverFacade.delete(id);
            return ResponseEntity.ok(new StandardResponse<>(messages.get("driver.delete.successful"), StandardResponse.StatusStandardResponse.OK));
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(messages.get("driver.delete.error"));
        }
    }

    @PostMapping("/save-driver-and-vehicle")
    @Operation(summary = "Permite guardar un conductor y su vehículo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = DriverDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            }, description = "El conductor y el vehículo fueron guardados exitosamente"),
            @ApiResponse(responseCode = "400", description = "La petición es inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta")})
    public ResponseEntity<StandardResponse<DriverDTO>> saveDriverAndVehicle(@Valid @RequestBody DriverDTO driver) {
        return ResponseEntity.ok(new StandardResponse<>(StandardResponse.StatusStandardResponse.OK,
                messages.get("driver.save.driver.and.vehicle.successful"),
                driverFacade.saveDriverAndVehicle(driver)));
    }

}
