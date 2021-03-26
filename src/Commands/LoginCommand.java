/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import ObjectStore.UserStore;

/**
 *
 * @author TJ
 */
public class LoginCommand implements Command{

    @Override
    public String execute() {
        UserStore inst = new UserStore();
        inst.register("qwe", "qweqwe");
        
        return "Hello World!";
    }
    
}
