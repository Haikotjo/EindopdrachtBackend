package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.common.ApiResponse;
import nl.novi.eindopdrachtbackend.dto.MenuItemDTO;
import nl.novi.eindopdrachtbackend.dto.MenuItemInputDTO;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing menu items.
 */
@RestController
@RequestMapping("/menuItems")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    /**
     * Get all MenuItems (Admin only)
     *
     * @return ResponseEntity containing a list of MenuItemsDTO objects
     */
    @GetMapping("/admin/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<MenuItemDTO>> getAllMenuItems() {
        List<MenuItemDTO> menuItems = menuItemService.getAllMenuItems();
        return new ResponseEntity<>(menuItems, HttpStatus.OK);
    }

    /**
     * Get all menu items for a specific owner (Admin only).
     *
     * @param ownerId owner ID
     * @return ResponseEntity containing a list of MenuItemDTO objects
     */
    @GetMapping("/admin/{ownerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<MenuItemDTO>> getAllMenuItemsForOwner(@PathVariable Long ownerId) {
        try {
            List<MenuItemDTO> menuItems = menuItemService.getAllMenuItemsForOwner(ownerId);
            return new ResponseEntity<>(menuItems, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createMenuItem(@RequestBody MenuItemInputDTO menuItemInputDTO) {
        MenuItemDTO createdMenuItemDTO = menuItemService.createMenuItem(menuItemInputDTO);
        ApiResponse apiResponse = new ApiResponse(true, "MenuItem successfully created.", createdMenuItemDTO);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateMenuItem(@PathVariable Long id, @RequestBody MenuItemInputDTO menuItemInputDTO) {
        MenuItemDTO updatedMenuItemDTO = menuItemService.updateMenuItem(id, menuItemInputDTO);
        ApiResponse apiResponse = new ApiResponse(true, "MenuItem succesfully updated.", updatedMenuItemDTO);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MenuItemDTO>> findByNameIgnoreCase(@RequestParam String name) {
        List<MenuItemDTO> menuItems = menuItemService.findByNameIgnoreCase(name);
        return new ResponseEntity<>(menuItems, HttpStatus.OK);
    }

    @PostMapping("/{menuItemId}/addIngredient/{ingredientId}")
    public ResponseEntity<Void> addIngredientToMenuItem(@PathVariable Long menuItemId, @PathVariable Long ingredientId) {
        menuItemService.addIngredientToMenuItem(menuItemId, ingredientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
