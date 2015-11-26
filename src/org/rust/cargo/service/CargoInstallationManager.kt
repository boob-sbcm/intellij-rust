package org.rust.cargo.service

import com.intellij.openapi.projectRoots.SdkType
import org.rust.cargo.util.Platform
import org.rust.lang.RustSdkType

class CargoInstallationManager {

    fun hasCargoMetadata(cargoHomePath: String?): Boolean {
        val sdk = SdkType.findInstance(RustSdkType::class.java)

        if (cargoHomePath == null || !sdk.isValidSdkHome(cargoHomePath))
            return false

        //
        // NOTE:
        //  Since `metadata` isn't made its way into Cargo bundle (yet),
        //  this particular check verifies whether user has it installed already or not.
        //  Hopefully based on the following lines
        //
        //  https://github.com/rust-lang/cargo/blob/master/src/bin/cargo.rs#L189 (`execute_subcommand`)
        //

        return Platform.runExecutableWith(
            sdk.getPathToExecInSDK(cargoHomePath, RustSdkType.CARGO_BINARY_NAME).absolutePath,
            arrayListOf(RustSdkType.CARGO_METADATA_SUBCOMMAND)
        ).exitCode == 0
    }
}
