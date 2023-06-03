package ru.netology.mycloudstorage.modele;

import com.sun.source.tree.CaseTree;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    //связываем роль с разрешениями
    USER(Set.of(Permission.DEVELOPERS_READ)),
    ADMIN(Set.of(Permission.DEVELOPERS_WRITE, Permission.DEVELOPERS_READ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions){
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    // конвертируем роли в гранд ассорити roli

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
