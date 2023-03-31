package com.omunoz.JavaStreamDynamicPredicate.DynamicBehaivor;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
public class RootModel<T> {

    private final T root;

    public Optional<String> getProperty(String property) {
        String value = null;
        try {
            value = BeanUtils.getProperty(root, property);
        }
        catch (InvocationTargetException e) {
            log.error("InvocationTargetException -> " + e.getMessage());
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException -> " + e.getMessage());
        } catch (NoSuchMethodException e) {
            log.error("NoSuchMethodException -> " + e.getMessage());
        }
        return Optional.ofNullable(value);
    }
    public T getRoot() {
        return root;
    }
}
