package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.common.ApiResponse;
import nl.novi.eindopdrachtbackend.dto.MenuDTO;
import nl.novi.eindopdrachtbackend.dto.MenuInputDTO;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.security.SecurityUtils;
import nl.novi.eindopdrachtbackend.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * Get all menus (Admin only)
     *
     * @return ResponseEntity containing a list of MenuDTO objects
     */
    @GetMapping("/admin/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<MenuDTO>> getAllMenus() {
        List<MenuDTO> menus = menuService.getAllMenus();
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    /**
     * Endpoint for owners to retrieve their own menus.
     *
     * @return ResponseEntity containing a list of MenuDTO objects
     */
    @GetMapping("/owner")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<List<MenuDTO>> getAllMenusForLoggedInOwner() {
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        try {
            List<MenuDTO> menus = menuService.getAllMenusForLoggedInOwner(currentUserEmail);
            return new ResponseEntity<>(menus, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






    @GetMapping("/{id}")
    public ResponseEntity<MenuDTO> getMenuById(@PathVariable Long id) {
        MenuDTO menu = menuService.getMenuById(id);
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createMenu(@RequestBody MenuInputDTO menuInputDTO) {
        MenuDTO createdMenuDTO = menuService.createMenu(menuInputDTO);
        ApiResponse apiResponse = new ApiResponse(true, "Menu successfully created.", createdMenuDTO);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateMenu(@PathVariable Long id, @RequestBody MenuInputDTO menuInputDTO) {
        MenuDTO updatedMenuDTO = menuService.updateMenu(id, menuInputDTO);
        ApiResponse apiResponse = new ApiResponse(true, "Menu successfully updated.", updatedMenuDTO);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MenuDTO>> findByNameIgnoreCase(@RequestParam String name) {
        List<MenuDTO> menus = menuService.findByNameIgnoreCase(name);
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @PostMapping("/{menuId}/addMenuItem/{menuItemId}")
    public ResponseEntity<Void> addMenuItemToMenu(@PathVariable Long menuId, @PathVariable Long menuItemId) {
        menuService.addMenuItemToMenu(menuId, menuItemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
