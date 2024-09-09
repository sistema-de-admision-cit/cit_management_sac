import { it, expect, describe, afterEach } from 'vitest'
import { render, screen, fireEvent, cleanup, waitFor } from '@testing-library/react'
import PercentagesConfiguratorView from '../../../../../src/components/modules/system_configuration/percentages/view/PercentagesConfiguratorView'
import '@testing-library/jest-dom/vitest'

describe('PercentagesConfiguratorView', () => {
  afterEach(() => {
    cleanup()
  })
})
