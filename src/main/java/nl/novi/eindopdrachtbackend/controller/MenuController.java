package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.common.ApiResponse;
import nl.novi.eindopdrachtbackend.dto.MenuDTO;
import nl.novi.eindopdrachtbackend.dto.MenuInputDTO;
import nl.novi.eindopdrachtbackend.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<MenuDTO>> getAllMenus() {
        List<MenuDTO> menus = menuService.getAllMenus();
        return new ResponseEntity<>(menus, HttpStatus.OK);
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
