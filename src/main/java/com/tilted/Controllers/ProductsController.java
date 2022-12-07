package com.tilted.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.tilted.Models.ProductDTO;
import com.tilted.Models.ValidationError;
import com.tilted.Services.ProductsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.Validation;
import java.util.HashMap;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/products")
public class ProductsController {
    private final ProductsService productService;

    public ProductsController(ProductsService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<ProductDTO> getAllProducts() {
        return productService.GetAll();
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id) {
        var productDTO = productService.GetById(id);
        return productDTO != null
                ? new ResponseEntity<>(productDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable int id) {
        return productService.DeleteById(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO product) {
        var createdProduct = productService.Create(product);
        var responseModel = new HashMap<String, Integer>();
        responseModel.put("id", createdProduct.Id);

        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @Valid @RequestBody ProductDTO product) {
        if (productService.GetById(id) == null) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        product.Id = id;
        productService.Update(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(produces = "application/json-patch+json", value="/{id}")
    public ResponseEntity<?> patchProduct(@PathVariable int id, @RequestBody JsonPatch jsonPatch) {
        var foundProduct = productService.GetById(id);
        if (foundProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            var objMapper = new ObjectMapper();
            var node = jsonPatch.apply(objMapper.convertValue(foundProduct, JsonNode.class));
            foundProduct = objMapper.treeToValue(node, ProductDTO.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            var constraints = validator.validate(foundProduct);

            if (!constraints.isEmpty()) {

                var validationError = new ValidationError();
                validationError.Status = "Bad request";
                constraints.forEach(x -> validationError.Errors.put(x.getPropertyPath().toString(), x.getMessage()));

                return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
            }

            productService.Update(foundProduct);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationError handleValidationErrors(MethodArgumentNotValidException ex) {
        var validationError = new ValidationError();
        validationError.Status = "Bad request";

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            var fieldName = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            validationError.Errors.put(fieldName, errorMessage);
        });

        return validationError;
    }
}
