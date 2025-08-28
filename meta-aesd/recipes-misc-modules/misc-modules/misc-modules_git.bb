# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "Unknown"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-JiaZhi96.git;protocol=ssh;branch=main \
           file://0001-build-only-misc-modules-and-scull.patch \
           file://0001-update-insmod-path.patch \
           file://misc-modules-start-stop \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "5977e695f291acdd7fa4aad2c79989f2d7a4e780"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR} M=${S}/misc-modules"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "misc-modules-start-stop"

FILES:${PN} += "${sysconfdir}/init.d/misc-modules-start-stop"
FILES:${PN} += "${bindir}/module_load ${bindir}/module_unload"

do_install() {
    oe_runmake -C ${S}/misc-modules modules_install INSTALL_MOD_PATH=${D}
    
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/misc-modules-start-stop ${D}${sysconfdir}/init.d

    install -d ${D}${bindir}
    install -m 0755 ${S}/misc-modules/module_load ${D}${bindir}
    install -m 0755 ${S}/misc-modules/module_unload ${D}${bindir}
}