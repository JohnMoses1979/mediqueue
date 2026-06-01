import React from "react";
import { Text, TouchableOpacity, StyleSheet } from "react-native";
import { LinearGradient } from "expo-linear-gradient";

export default function GradientButton({
  title,
  onPress,
  colors = ["#2563EB", "#06B6D4"],
  disabled = false,
  style,
}) {
  return (
    <TouchableOpacity
      activeOpacity={disabled ? 1 : 0.88}
      onPress={disabled ? undefined : onPress}
      disabled={disabled}
      style={style}
    >
      <LinearGradient
        colors={colors}
        style={[styles.button, disabled && styles.disabledButton]}
      >
        <Text style={[styles.text, disabled && styles.disabledText]}>{title}</Text>
      </LinearGradient>
    </TouchableOpacity>
  );
}

const styles = StyleSheet.create({
  button: {
    height: 54,
    borderRadius: 18,
    alignItems: "center",
    justifyContent: "center",
  },
  disabledButton: {
    opacity: 0.65,
  },
  text: {
    color: "#fff",
    fontWeight: "900",
    fontSize: 16,
  },
  disabledText: {
    color: "#F8FAFC",
  },
});

