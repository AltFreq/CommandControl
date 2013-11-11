package com.minecraftdimensions.command.config;

import java.util.Map;

public interface ConfigurationSerializable {
    public Map<String, Object> serialize();
}