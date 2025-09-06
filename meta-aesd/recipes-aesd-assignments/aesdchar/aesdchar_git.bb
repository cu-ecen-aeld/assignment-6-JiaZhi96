# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=867129fc4d4aeeba7bade00d1aea9f87"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-JiaZhi96.git;protocol=ssh;branch=main \
           file://aesdchar-start-stop \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "7cabab06caaae4f7ba7780d963a50e37a4a7652a"

S = "${WORKDIR}/git/aesd-char-driver/"

inherit module

EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR} M=${S}/"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdchar-start-stop"

FILES:${PN} += "${sysconfdir}/init.d/aesdchar-start-stop"
FILES:${PN} += "${bindir}/aesdchar_load ${bindir}/aesdchar_unload"

do_install:append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/aesdchar-start-stop ${D}${sysconfdir}/init.d

    install -d ${D}${bindir}
    install -m 0755 ${S}/aesdchar_load ${D}${bindir}
    install -m 0755 ${S}/aesdchar_unload ${D}${bindir}
}