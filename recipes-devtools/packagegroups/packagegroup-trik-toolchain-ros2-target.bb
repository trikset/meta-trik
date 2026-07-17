SUMMARY = "ROS2 Humble dev headers and cmake configs for TRIK SDK"
LICENSE = "MIT"

PACKAGE_ARCH = "${TUNE_PKGARCH}"

inherit packagegroup

# Headers and cmake configs for writing ROS2 C++ nodes against TRIK target
RDEPENDS:${PN} += " \
    rclcpp-dev \
    rcl-dev \
    rcl-interfaces-dev \
    rcl-lifecycle-dev \
    rclcpp-lifecycle-dev \
    rclcpp-components-dev \
    rclcpp-action-dev \
    rosidl-runtime-c-dev \
    rosidl-runtime-cpp-dev \
    rosidl-typesupport-interface-dev \
    rosidl-typesupport-introspection-c-dev \
    rosidl-typesupport-introspection-cpp-dev \
    cyclonedds-dev \
    rmw-dev \
    rmw-cyclonedds-cpp-dev \
    std-msgs-dev \
    std-srvs-dev \
    builtin-interfaces-dev \
    rcl-interfaces-dev \
    action-msgs-dev \
    lifecycle-msgs-dev \
    composition-interfaces-dev \
    common-interfaces-dev \
    ament-cmake-dev \
    ament-index-cpp-dev \
"
