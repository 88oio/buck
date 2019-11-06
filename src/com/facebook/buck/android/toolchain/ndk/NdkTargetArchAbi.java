/*
 * Copyright 2017-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.facebook.buck.android.toolchain.ndk;

import com.facebook.buck.core.exceptions.HumanReadableException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/** Name of the target CPU + ABI. */
public enum NdkTargetArchAbi {
  X86("x86", "x86"),
  X86_64("x86_64", "x86_64"),
  ARMEABI("armeabi", "arm"),
  ARMEABI_V7A("armeabi-v7a", "armv7"),
  ARM64_V8A("arm64-v8a", "arm64"),
  ;

  private final String value;
  private final String buckconfigValue;

  NdkTargetArchAbi(String value, String buckconfigValue) {
    this.value = Objects.requireNonNull(value);
    this.buckconfigValue = buckconfigValue;
  }

  @Override
  public String toString() {
    return value;
  }

  /** Get a value from a string used in buckconfig, throw if ABI is unknown. */
  public static NdkTargetArchAbi fromBuckconfigValue(String buckconfigValue) {
    for (NdkTargetArchAbi value : NdkTargetArchAbi.values()) {
      if (value.buckconfigValue.equals(buckconfigValue)) {
        return value;
      }
    }
    String abis =
        Arrays.stream(NdkTargetArchAbi.values())
            .map(a -> a.buckconfigValue)
            .collect(Collectors.joining(", "));
    throw new HumanReadableException(
        "unknown NDK abi: %s, possible values: %s", buckconfigValue, abis);
  }
}
